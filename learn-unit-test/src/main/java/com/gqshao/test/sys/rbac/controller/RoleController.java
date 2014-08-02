package com.gqshao.test.sys.rbac.controller;

import java.util.*;

import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.gqshao.test.commons.persistence.domain.*;
import com.gqshao.test.sys.rbac.domain.*;
import com.gqshao.test.sys.rbac.filter.*;
import com.gqshao.test.sys.rbac.service.*;

/**
 * <pre>
 * 功能说明：角色Controller
 * </pre>
 */
@Controller
@RequestMapping(value = "/sys/rbac/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 初始化
     *
     * @return
     */
    //@RequiresPermissions("sysmgt:role")
    @RequestMapping
    public ModelAndView init() {

        ModelAndView mav = new ModelAndView("sys/rbac/role/list");

        return mav;
    }

    /**
     * 列表  
     * @return  JSON数据
     */
    //@RequiresPermissions("sysmgt:role")
    @RequestMapping(value = "getList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JqgridResponse<Role> getAll(RoleFilter filter) {
        
        List<Role> list = roleService.getRolesByFilter(filter);
        
        JqgridResponse<Role> response = JqgridResponseContext.getJqgridResponse(Role.class);

        response.setRows(list);
        
        return response;
    }
    
    /***
     * 初始化编辑页面
     * 
     * @param id
     * @param model
     * @return
     */
   // @RequiresPermissions(value={"rolemgt:add","rolemgt:update"},logical = Logical.OR)
    @RequestMapping(value = "openmodalroleinput", method = RequestMethod.GET)
    public ModelAndView roleInput(
            @RequestParam(value = "id", required = false) String id) {
        ModelAndView mav = new ModelAndView("sys/rbac/role/form");
        
        if (!StringUtils.isBlank(id)) {
            
        	Role role = roleService.getById(id);
            
        	mav.addObject("role", role);
        	
        	List<Permission> permissions = permissionService.getPermissionsByRoleId(id);
        	
        	mav.addObject("ownPermissions", permissions);
        }
        return mav;
    }
    
    /**
     * 该租户下所有权限数据
     * @return
     */
    @RequestMapping(value = "getAllPermission", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Permission> getPermissions(){
        
        return permissionService.getAllPermissions();
    
    }
    
    
    /**
     * 添加角色权限
     * @param role
     * @param pIds
     * @return
     */
   // @RequiresPermissions("rolemgt:add")
    @RequestMapping(value="save",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean save(Role role,@RequestParam(value="pIds",required=false)String pIds){
        
        if(StringUtils.isNotBlank(pIds)){
            
            String[] ids = pIds.split(",");
            
            roleService.save(role,ids);
        }else{
            
            roleService.save(role);
        }
        return true;
    }
    
    /**
     * 保存角色权限
     * @param role
     * @param pIds
     * @return
     */
  //  @RequiresPermissions("rolemgt:update")
    @RequestMapping(value="update",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean update(Role role,@RequestParam(value="pIds",required=false)String pIds){
        
        if(StringUtils.isNotBlank(pIds)){
            
            String[] ids = pIds.split(",");
            
            roleService.update(role,ids);
        }else{
            
            roleService.update(role,null);
        }
        return true;
    }
    
    /**
     * 删除角色
     * @param ids
     * @return
     */
    //@RequiresPermissions("rolemgt:delete")
    @RequestMapping(value="delete",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean delete(@RequestParam(value="ids",required=true)String ids){
        
        String[] sp = ids.split(",");
        
        roleService.deleteByIds(sp);
        
        return true;
    }
    
    /**
     * 查找关联用户数量
     * @param ids
     * @return
     */
    @RequestMapping(value="getUserRoleCount",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Long getUserRoleCount(@RequestParam(value="ids",required=true)String ids){
    	 String[] sp = ids.split(",");
    	return roleService.getUserRoleCount(sp);
    }
    
    @RequestMapping(value="/checkExists")
    public @ResponseBody Boolean checkExists(String name,@RequestParam(value="id",required=false)String id){
    	return roleService.checkExists(name, id);
    }
  	
}

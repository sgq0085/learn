package com.gqshao.test.sys.rbac.controller;

import java.util.*;

import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.gqshao.test.sys.rbac.domain.*;
import com.gqshao.test.sys.rbac.service.*;


/**
 * <pre>
 * 功能说明：权限管理Controller
 * </pre>
 */
@Controller
@RequestMapping(value = "sys/rbac/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	//@RequiresPermissions("permissionmgt:administrator")
	@RequestMapping
	public ModelAndView init() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sys/rbac/permission/list");

		return mav;
	}

	//@RequiresPermissions("sysmgt:permission")
	@RequestMapping(value = "getAllPermission", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Permission> getAllPermissions() {
		List<Permission> listPermission = permissionService.getAllPermissions();
		return listPermission;
	}
	
	//@RequiresPermissions("permissionmgt:add")
	@RequestMapping(value="add",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean addPermission(@Valid Permission permission,Errors errors){
		if(errors.hasErrors()){
			return false;
		}
		try{
			permissionService.save(permission);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//@RequiresPermissions("permissionmgt:delete")
	@RequestMapping(value="delete",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean delete(@RequestParam(value="id",required=true)String id){
		try{
			permissionService.delete(id);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	//@RequiresPermissions("permissionmgt:update")
	@RequestMapping(value="update",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean update(@Valid Permission permission,Errors errors){
		if(errors.hasErrors()){
			for (FieldError error : errors.getFieldErrors()){
				System.out.println(error.getField());
			}
			return false;
		}
		try{
			permissionService.update(permission);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}

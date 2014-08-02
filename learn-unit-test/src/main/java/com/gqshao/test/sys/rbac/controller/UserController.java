package com.gqshao.test.sys.rbac.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gqshao.test.commons.beanvalidator.BeanValidators;
import com.gqshao.test.commons.persistence.domain.JqgridResponse;
import com.gqshao.test.commons.persistence.domain.JqgridResponseContext;
import com.gqshao.test.commons.security.utils.Securitys;
import com.gqshao.test.commons.utils.Ids;
import com.gqshao.test.sys.rbac.domain.User;
import com.gqshao.test.sys.rbac.domain.UserDTO;
import com.gqshao.test.sys.rbac.filter.UserFilter;
import com.gqshao.test.sys.rbac.service.RoleService;
import com.gqshao.test.sys.rbac.service.UserService;

@Controller
@RequestMapping(value = "/sys/rbac/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Validator validator;

    /**
     * 初始化
     *
     * @return
     */
    @RequiresPermissions("sys_user")
    @RequestMapping
    public ModelAndView init() {

        ModelAndView mav = new ModelAndView("/sys/rbac/user/list");

        return mav;
    }

    /**
     * 初始化编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions(value = {"user_edit", "user_add"}, logical = Logical.OR)
    @RequestMapping(value = "input", method = RequestMethod.GET)
    public ModelAndView userInput(@RequestParam(value = "id", required = false) String id) {

        Map<String, Object> res = Maps.newHashMap();
        res.put("success", true);
        List<User> users = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        User user1 = new User();
        user1.setId(Ids.uuid());
        user1.setLoginName("user1");
        users.add(user1);
        User user2 = new User();
        user2.setId(Ids.uuid());
        user2.setLoginName("user2");
        users.add(user2);
        User user3 = new User();
        user3.setId(Ids.uuid());
        user3.setLoginName("user3");
        users.add(user3);
        res.put("data", users);
        return new ModelAndView("sys/rbac/user/form", res);
        // ModelAndView mav = new ModelAndView("sys/rbac/user/form");
        //
        // if (!StringUtils.isBlank(id)) {
        //
        // User user = userService.getById(id);
        //
        // mav.addObject("user", user);
        //
        // // 用户拥有的角色
        // mav.addObject("ownRoles", roleService.getRolesByUserId(id));
        //
        // }
        //
        // mav.addObject("roles", roleService.getAll());
        //
        // return mav;
    }

    /**
     * 查询用户列表
     *
     * @param filter
     * @return
     */
    @RequiresPermissions("sys_user")
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JqgridResponse<UserDTO> list(UserFilter filter) {

        List<UserDTO> userList = userService.list(filter);

        JqgridResponse<UserDTO> response = JqgridResponseContext.getJqgridResponse(UserDTO.class);

        response.setRows(userList);

        return response;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequiresPermissions("user_add")
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(User user, @RequestParam(value = "rIds", required = false) String rIds) {

        // 调用JSR303 Bean Validator进行校验, 异常将由ConstraintViolationExceptionHandler统一处理.
        BeanValidators.validateWithException(validator, user);

        user.setIsAdmin(null);
        if (StringUtils.isNotBlank(rIds)) {
            List<String> list = new ArrayList<String>();
            String[] rids = rIds.split(",");

            userService.save(user, rids);
        } else {

            userService.save(user);
        }

        Map<String, Object> res = new HashMap<String, Object>();

        res.put("success", true);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @RequiresPermissions("user_edit")
    @RequestMapping(value = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(User user, @RequestParam(value = "rIds", required = false) String rIds) {

        // 调用JSR303 Bean Validator进行校验, 异常将由ConstraintViolationExceptionHandler统一处理.
        BeanValidators.validateWithException(validator, user);

        if (StringUtils.isNotBlank(rIds)) {

            String[] rids = rIds.split(",");

            userService.update(user, rids);

        } else {
            userService.update(user, null);
        }

        Map<String, Object> res = new HashMap<String, Object>();

        res.put("success", true);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("user_delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@RequestParam(value = "ids", required = true) String ids) {

        String[] idArr = ids.split(",");

        userService.delete(idArr);

        Map<String, Object> res = new HashMap<String, Object>();

        res.put("success", true);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    /**
     * 检查用户名是否存在
     *
     * @param loginName
     * @param id
     * @return
     */
    @RequestMapping(value = "checkLoginNameExists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> checkLoginNameExists(
            @RequestParam(value = "loginName", required = true) String loginName,
            @RequestParam(value = "id", required = false) String id) {

        Boolean result = userService.chekcLoginNameExists(loginName, id);

        Map<String, Object> res = new HashMap<String, Object>();

        res.put("result", result);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    /**
     * 检查旧密码是否有效
     *
     * @param id
     * @param password
     * @return
     */
    @RequestMapping(value = "checkConfirmOldPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> checkConfirmOldPassword(@RequestParam(value = "id", required = false) String id,
                                                     @RequestParam(value = "password", required = true) String password) {

        Boolean result = userService.checkConfirmOldPassword(id, password);

        Map<String, Object> res = new HashMap<String, Object>();

        res.put("result", result);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    /**
     * 重置密码
     *
     * @param id
     * @param newPassword
     * @return
     */
    @RequiresPermissions("user_reset_pwd")
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestParam(value = "id", required = true) String id,
                                           @RequestParam(value = "newPassword", required = true) String newPassword) {

        userService.resetPassword(id, newPassword);

        Map<String, Object> res = new HashMap<String, Object>();

        res.put("success", true);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequiresPermissions("user_ch_pwd")
    @RequestMapping(value = "modifyPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> modifyPassword(
            @RequestParam(value = "currentPassword", required = true) String currentPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword,
            @RequestParam(value = "id", required = false) String id) {

        userService.modifyPassword(currentPassword, newPassword, id);

        Map<String, Object> res = new HashMap<String, Object>();

        res.put("success", true);
        SecurityUtils.getSubject().logout();
        return new ResponseEntity(res, HttpStatus.OK);
    }

    /**
     * 初始化修改密码页面
     *
     * @return
     */
    @RequiresPermissions("user_ch_pwd")
    @RequestMapping(value = "openmodalupdatepassword", method = RequestMethod.GET)
    public ModelAndView updatePassword(@RequestParam(value = "id", required = false) String id) {

        ModelAndView mav = new ModelAndView("sys/rbac/user/updatePasswordForm");

        if (StringUtils.isBlank(id)) {

            id = Securitys.getUserId();
        }

        mav.addObject("user", userService.getById(id));

        return mav;
    }
}

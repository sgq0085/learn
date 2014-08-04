package com.gqshao.cas.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 配合shiro-cas
 */
@Controller
public class ShiroCasTmpController {

    /**
     * shiro-cas登录失败后，通过该控制器跳转到失败页面
     */
    @RequestMapping("/cas-failure")
    public String casFailure() {
        return "casFailure";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("show-info:show-div1")
    @RequestMapping("/shiro-info")
    @SuppressWarnings("unchecked")
    public String shiroInfo() {
        DelegatingSubject subject = (DelegatingSubject) SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        List<Object> listPrincipals = principalCollection.asList();
        String name = listPrincipals.get(0).toString();
        Map<String, String> attributes = (Map<String, String>) listPrincipals.get(1);
        return "shiroInfo";
    }


}

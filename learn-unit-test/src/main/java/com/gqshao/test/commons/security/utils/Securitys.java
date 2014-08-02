package com.gqshao.test.commons.security.utils;

import java.util.*;

import org.apache.shiro.*;

import com.gqshao.test.commons.security.shiro.*;
import com.gqshao.test.commons.utils.*;

/**
 * 功能说明：安全相关工具类
 */
public class Securitys extends SecurityUtils {

    /***
     * 获取ShiroUser
     */
    public static ShiroUser getUser() {
        return (ShiroUser) getSubject().getPrincipal();
    }

    /***
     * 获取用户ID
     */
    public static String getUserId() {
        return getUser().getId();
    }

    /***
     * 获取登录名
     */
    public static String getLoginName() {
        return getUser().getLoginName();
    }

    /***
     * 获取用户名
     */
    public static String getName() {
        return getUser().getName();
    }

    /**
     * 获取当前用户IP
     */
    public static String getIp() {
        return getUser().getIp();
    }

    /**
     * 是否为超级管理员
     */
    public static boolean isAdmin() {
        return getUser().isAdmin();
    }

    /**
     * 返回权限列表
     */
    public static List<String> getPermissions() {
        return getUser().getPermissions();
    }
    
    
    /**
     * 返回加密后的密码
     * @param password 原密码
     * @param salts 盐
     * @param iterations 加密次数
     */
    public static String getEntryptPassword(String password, byte[] salts, int iterations) {
        byte[] hashPassword = Digests.sha1(password.getBytes(), salts, iterations);
        return Encodes.encodeHex(hashPassword);
    }

}

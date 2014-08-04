package com.gqshao.cas.realm;

import com.google.common.collect.Sets;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2014/8/3.
 */
public class MyCasRealm extends CasRealm {


    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        return super.doGetAuthenticationInfo(authcToken);
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        List<Object> listPrincipals = principals.asList();
        String name = listPrincipals.get(0).toString();
        Map<String, String> attributes = (Map<String, String>) listPrincipals.get(1);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = Sets.newHashSet();
        roles.add("admin");
        roles.add("operator");
        authorizationInfo.setRoles(roles);
        Set<String> permissions = Sets.newHashSet();
        permissions.add("show-info");
//        permissions.add("show-info:show-div1");
//        permissions.add("show-info:show-div2");
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }
}


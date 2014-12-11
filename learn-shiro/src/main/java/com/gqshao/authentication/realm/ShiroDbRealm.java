package com.gqshao.authentication.realm;

import com.google.common.collect.Lists;
import com.gqshao.authentication.domain.CustomToken;
import com.gqshao.authentication.domain.ShiroUser;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;

    public ShiroDbRealm() {
        super();
        setAuthenticationTokenClass(CustomToken.class);
    }

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        CustomToken token = (CustomToken) authcToken;
        String loginName = token.getLoginName();
        String host = token.getHost();
        ShiroUser su = new ShiroUser.Builder("id", loginName).name("name").isAdmin(true).ip(host).builder();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(su, token.getPassword(), getName());
        return info;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 添加角色
        info.addRole("admin");

        // 添加权限
        List<String> permList = Lists.newArrayList();
        permList.add("permission_admin");
        info.addStringPermissions(permList);

        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
//    @PostConstruct
//    public void initCredentialsMatcher() {
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
//        matcher.setHashIterations(HASH_INTERATIONS);
//        setCredentialsMatcher(matcher);
//    }

}

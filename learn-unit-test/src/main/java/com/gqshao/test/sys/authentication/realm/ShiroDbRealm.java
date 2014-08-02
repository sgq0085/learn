package com.gqshao.test.sys.authentication.realm;

import java.util.*;

import javax.annotation.*;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.*;
import org.apache.shiro.authz.*;
import org.apache.shiro.realm.*;
import org.apache.shiro.subject.*;
import org.apache.shiro.util.*;
import org.springframework.beans.factory.annotation.*;

import com.gqshao.test.commons.security.shiro.*;
import com.gqshao.test.commons.utils.*;
import com.gqshao.test.sys.authentication.domain.*;
import com.gqshao.test.sys.rbac.domain.*;
import com.gqshao.test.sys.rbac.domain.Permission;
import com.gqshao.test.sys.rbac.service.*;

public class ShiroDbRealm extends AuthorizingRealm {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;

    @Autowired
    private UserService userService;

    @Autowired
    protected PermissionService permissionService;
    @Autowired
    protected RoleService roleService;

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
        User user = userService.getUserByLoginName(loginName);
        if (user != null) {
            ShiroUser root = new ShiroUser.Builder(user.getId(), loginName).name(user.getName())
                    .isAdmin(user.getIsAdmin() == 1 ? true : false).ip(host).builder();
            byte[] salt = Encodes.decodeHex(user.getSalt());
            return new SimpleAuthenticationInfo(root, user.getPassword(), ByteSource.Util.bytes(salt),
                    getName());
        } else {
            return null;
        }

    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<Role> roleList = null;// 角色集合//
        List<String> pList = new ArrayList<String>();
        List<Permission> listP = null;// 权限集合//

        if (shiroUser.isAdmin()) {
            // 超级管理员获取所有角色//
            roleList = roleService.getAll();
            // 超级管理员获取所有权限//
            listP = permissionService.getAllPermissions();
        } else {
            roleList = roleService.getRolesByUserId(shiroUser.getId());
            listP = permissionService.getByUserId(shiroUser.getId());
        }
        // 遍历角色//
        for (Role role : roleList) {
            info.addRole(role.getName());
        }
        // 遍历权限//
        for (Permission per : listP) {
            if (per != null) {
                String[] ps = per.getCode().split(";");
                for (int i = 0; i < ps.length; i++) {
                    pList.add(ps[i]);
                }
            }
        }
        if (shiroUser.isAdmin()) {
            pList.add("permissionmgt:administrator");
        }
        // 基于Permission的权限信息//
        info.addStringPermissions(pList);
        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
        matcher.setHashIterations(HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

}

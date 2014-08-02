package com.gqshao.test.sys.rbac.service.impl;

import com.gqshao.test.commons.exception.ServiceException;
import com.gqshao.test.commons.security.utils.Digests;
import com.gqshao.test.commons.security.utils.Securitys;
import com.gqshao.test.commons.utils.Encodes;
import com.gqshao.test.commons.utils.Ids;
import com.gqshao.test.sys.authentication.realm.ShiroDbRealm;
import com.gqshao.test.sys.rbac.dao.UserDao;
import com.gqshao.test.sys.rbac.domain.User;
import com.gqshao.test.sys.rbac.domain.UserDTO;
import com.gqshao.test.sys.rbac.domain.UserRoleDTO;
import com.gqshao.test.sys.rbac.filter.UserFilter;
import com.gqshao.test.sys.rbac.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * <pre>
 * 功能说明：用户Service
 * </pre>
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByLoginName(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        try {
            return userDao.findUserByLoginName(loginName);
        } catch (Exception e) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("msg", "查找用户失败");
            e.printStackTrace();
            // logService.error(Consts.SystemModel.SYS, "查找用户失败");
            throw new ServiceException("login", params, e);
        }
        // @Test(expected=...)
        // @Test(timeout=...)
        // @Timed
        // @Repeat
        // @Ignore
        // @ProfileValueSourceConfiguration
        // @IfProfileValue
    }

    @Override
    public User getById(String id) {
        try {
            return userDao.findUserById(id);
        } catch (Exception e) {
            // logService.error(Consts.SystemModel.SYS, "查找用户失败");
            throw new ServiceException("查找用户失败", e);
        }

    }

    @Override
    public List<UserDTO> list(UserFilter filter) {
        try {
            return userDao.list(filter);
        } catch (Exception e) {
            // logService.error(Consts.SystemModel.SYS, "查询用户列表失败");
            throw new ServiceException("查询用户列表失败", e);
        }
    }

    @Override
    public String save(User user) {
        User oldUser = userDao.findUserByLoginName(user.getLoginName());
        if (oldUser != null) {
            throw new ServiceException("已存在相同登录名");
        }
        try {
            String userId = Ids.uuid();
            user.setId(userId);
            // 获取8位盐
            byte[] salts = Digests.generateSalt(ShiroDbRealm.SALT_SIZE);
            user.setSalt(Encodes.encodeHex(salts));
            user.setPassword(Securitys.getEntryptPassword(user.getPassword(), salts,
                    ShiroDbRealm.HASH_INTERATIONS));
            user.setCreateUser(Securitys.getUserId());
            user.setCreateDatetime(new Date());
            user.setIsDelete(0);

            if (null == user.getIsAdmin()) {
                user.setIsAdmin(0);
            }
            userDao.save(user);
            logger.info("用户" + Securitys.getName() + "创建用户:" + user.getName());
            return userId;
        } catch (Exception e) {
            throw new ServiceException("新增用户失败", e);
        }
    }

    @Override
    public String update(User user) {

        String oldId = user.getId();

        if (StringUtils.isBlank(oldId)) {
            throw new ServiceException("请选择要修改的用户！");
        }
        User oldUser = userDao.findUserByLoginName(user.getLoginName());
        if (oldUser != null && !oldId.equals(oldUser.getId())) {
            throw new ServiceException("已存在相同登录名");
        }
        try {
            user.setUpdateDatetime(new Date());
            user.setUpdateUser(Securitys.getUserId());
            userDao.update(user);

            // logService.info(Consts.SystemModel.SYS, "用户被更新"+user.getLoginName());

            return oldId;
        } catch (Exception e) {
            // logService.error(Consts.SystemModel.SYS, "修改用户失败");
            throw new ServiceException("修改用户失败id=" + oldId, e);
        }
    }

    @Override
    public void delete(String[] ids) {
        try {
            userDao.delete(ids);
            userDao.deleteUserRoles(ids);

        } catch (Exception e) {
            // logService.error(Consts.SystemModel.SYS, "删除用户失败");
            throw new ServiceException("删除用户失败", e);
        }
    }

    @Override
    public void resetPassword(String id, String newPassword) {

        User user = this.getById(id);
        if (null == user) {
            throw new ServiceException("请选择要重置密码的用户");
        }
        try {
            byte[] salts = Digests.generateSalt(ShiroDbRealm.SALT_SIZE);
            user.setSalt(Encodes.encodeHex(salts));
            user.setPassword(Securitys.getEntryptPassword(newPassword, salts, ShiroDbRealm.HASH_INTERATIONS));
            userDao.update(user);
        } catch (Exception e) {
            throw new ServiceException("用户密码重置失败", e);
        }
    }

    @Override
    public Boolean chekcLoginNameExists(String loginName, String id) {

        User oldUser;
        try {
            oldUser = userDao.findUserByLoginName(loginName.trim());
        } catch (Exception e) {

            // logService.error(Consts.SystemModel.SYS,"根据租户id和登陆名查询失败");
            throw new ServiceException("根据租户id和登陆名查询失败", e);
        }

        if (StringUtils.isNotBlank(id)) {
            if (null != oldUser && !id.equals(oldUser.getId())) {
                return true;
            }
            return false;
        }

        if (null != oldUser) {

            return true;
        }

        return false;
    }

    @Override
    public Boolean checkConfirmOldPassword(String userId, String password) {
        if (StringUtils.isBlank(userId)) {
            userId = Securitys.getUserId();
        }
        User user = getById(userId);

        if (null == user) {
            return false;
        }
        byte[] salt = Encodes.decodeHex(user.getSalt());
        String pwd = Securitys.getEntryptPassword(password, salt, ShiroDbRealm.SALT_SIZE);
        if (!user.getPassword().equals(pwd)) {
            return false;
        }
        return true;
    }

    @Override
    public void modifyPassword(String currentPassword, String newPassword, String id) {

        if (StringUtils.isBlank(id)) {
            id = Securitys.getUserId();
        }
        User user = getById(id);
        if (null == user) {
            throw new ServiceException("用户不存在");
        }
        byte[] salts = Encodes.decodeHex(user.getSalt());
        String pwd = Securitys.getEntryptPassword(currentPassword, salts, ShiroDbRealm.HASH_INTERATIONS);
        if (!user.getPassword().equals(pwd)) {
            throw new ServiceException("旧密码不正确");
        }
        String npwd = Securitys.getEntryptPassword(newPassword, salts, ShiroDbRealm.HASH_INTERATIONS);
        if (user.getPassword().equals(npwd)) {
            throw new ServiceException("新密码不能与旧密码一致");
        }
        byte[] newSalts = Digests.generateSalt(ShiroDbRealm.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(newSalts));
        user.setPassword(Securitys.getEntryptPassword(newPassword, newSalts, ShiroDbRealm.HASH_INTERATIONS));
        try {
            userDao.update(user);
        } catch (Exception e) {
            throw new ServiceException("修改密码失败！", e);

        }
    }

    @Override
    public String save(User user, String[] roles) {

        return saveOrUpdateUserRole(user, roles, false);

    }

    @Override
    public String update(User user, String[] roles) {

        return saveOrUpdateUserRole(user, roles, true);
    }

    @Override
    public String saveOrUpdateUserRole(User user, String[] roles, Boolean isUpdate) {

        String userId = null;

        // 如果更新，删除用户之前的角色
        if (null != isUpdate && isUpdate) {
            // 更新用户
            userId = update(user);

            // 删除用户之前的权限
            try {
                userDao.deleteUserRole(userId);
            } catch (Exception e) {
                // logService.error(Consts.SystemModel.SYS, "删除用户角色失败"+user.getName());
            }

            // logService.warning(Consts.SystemModel.SYS, "用户角色被删除"+user.getName());

        } else {

            // 保存用户
            userId = save(user);
        }

        if (null == roles || roles.length == 0) {
            return userId;
        }

        List<UserRoleDTO> urList = new ArrayList<UserRoleDTO>();

        UserRoleDTO ur = null;

        // 构造用户权限集合
        for (String roleId : roles) {

            ur = new UserRoleDTO(userId, roleId);

            urList.add(ur);
        }

        // 更新权限
        if (!urList.isEmpty()) {
            try {

                userDao.saveUserRole(urList);

                // logService.warning(Consts.SystemModel.SYS, Securitys.getLoginName()+"更新"+user.getLoginName()+"的角色");
            } catch (Exception e) {
                // logService.error(Consts.SystemModel.SYS, Securitys.getLoginName()+"保存用户角色失败");
                throw new ServiceException("保存用户角色失败", e);
            }
        }
        return userId;
    }
}

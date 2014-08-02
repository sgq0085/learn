package com.gqshao.test.sys.rbac.service.impl;

import java.util.*;

import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.gqshao.test.commons.exception.*;
import com.gqshao.test.commons.security.utils.*;
import com.gqshao.test.commons.utils.*;
import com.gqshao.test.sys.rbac.dao.*;
import com.gqshao.test.sys.rbac.domain.*;
import com.gqshao.test.sys.rbac.filter.*;
import com.gqshao.test.sys.rbac.service.*;


/**
 * <pre>
 * 功能说明：角色Service实现
 * </pre>
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> getRolesByUserId(String userId) {
		try {
			return roleDao.queryByUserId(userId);
		} catch (Exception e) {
			throw new ServiceException("查询角色失败", e);
		}
	}
	
	
	@Override
	public String save(Role role) {

		String roleId = Ids.uuid();
		role.setId(roleId);
		role.setCreateUser(Securitys.getUserId());
		role.setCreateDatetime(new Date());
		role.setIsDelete(0);
		if (checkExists(role.getName(), null)) {
			throw new ServiceException("角色名称已存在");
		}
		try {
			Integer indexNo = roleDao.queryMaxIndexNo();
			role.setIndexNo(indexNo);
			roleDao.save(role);
			//logService.info(Consts.SystemModel.SYS, "添加角色" + role.getName());
			return roleId;
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "添加角色失败");
			throw new ServiceException("添加角色失败", e);
		}

	}

	@Override
	public Role getById(String roleId) {
		try {
			return roleDao.queryById(roleId);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "根据用户查询角色失败");
			throw new ServiceException("根据用户查询角色失败", e);
		}
	}

	@Override
	public List<Role> getAll() {
		try {
			return roleDao.queryAll();
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "查询所有用户失败");
			throw new ServiceException("查询所有用户失败", e);
		}
	}

	@Override
	public List<Role> getRolesByFilter(RoleFilter filter) {
	
		try {
			return roleDao.queryByFilter(filter);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "按条件查询角色失败");
			throw new ServiceException("按条件查询角色失败", e);
		}
	}

	@Override
	public String update(Role role) {

		role.setUpdateDatetime(new Date());

		role.setUpdateUser(Securitys.getUserId());
		if (checkExists(role.getName(), role.getId())) {
			throw new ServiceException("角色名称已存在");
		}
		try {
			roleDao.update(role);

			//logService.info(Consts.SystemModel.SYS, "角色更新");

			return role.getId();

		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "更新角色失败");
			throw new ServiceException("更新角色失败", e);
		}
	}

	@Override
	public void delete(String id) {

		try {
			String[] ids = { id };
			if (roleDao.queryUserRoleCount(ids) > 0) {
				throw new ServiceException("角色下存在用户关联不能删除");
			}
			roleDao.delete(id);
			roleDao.deleteRolePermission(id);
			//logService.warning(Consts.SystemModel.SYS, "删除角色" + id);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "删除角色失败");
			throw new ServiceException("删除角色失败", e);
		}

	}

	@Override
	public void deleteByIds(String[] ids) {
		try {
			if (roleDao.queryUserRoleCount(ids) > 0) {
				throw new ServiceException("角色下存在用户关联不能删除");
			}
			roleDao.deleteByIds(ids);
			roleDao.deleteRolePermissions(ids);
			//logService.warning(Consts.SystemModel.SYS, "批量删除角色");
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "批量删除角色失败");
			throw new ServiceException("批量删除角色失败", e);
		}

	}

	@Override
	public String save(Role role, String[] permissions) {

		return saveOrUpdateTenantModule(role, permissions, null);
	}

	@Override
	public String update(Role role, String[] permissions) {

		return saveOrUpdateTenantModule(role, permissions, true);
	}

	/**
	 * 保存或更新角色权限
	 * 
	 * @param role
	 * @param permissions
	 * @param isUpdate
	 * @return
	 */
	private String saveOrUpdateTenantModule(Role role, String[] permissions,
			Boolean isUpdate) {

		String roleId = null;

		// 如果更新，删除该角色之前的权限
		if (null != isUpdate && isUpdate) {
			// 更新角色
			roleId = update(role);

			try {
				// 删除该角色之前的权限
				roleDao.deleteRolePermission(roleId);
				// logService.warning(Consts.SystemModel.SYS, "删除角色关联的权限");
			} catch (Exception e) {
				//logService.error(Consts.SystemModel.SYS, "删除角色关联的权限失败");
				throw new ServiceException("删除角色关联的权限失败", e);
			}

		} else {

			// 保存角色
			roleId = save(role);
		}

		if (null == permissions || permissions.length == 0) {
			return roleId;
		}

		List<RolePermissionDTO> rpList = new ArrayList<RolePermissionDTO>();

		RolePermissionDTO rp = null;

		// 构造角色权限集合
		for (String permissionId : permissions) {

			rp = new RolePermissionDTO(roleId, permissionId);

			rpList.add(rp);
		}

		// 更新角色权限
		if (!rpList.isEmpty()) {
			try {

				roleDao.saveRolePermission(rpList);
				//logService.info(Consts.SystemModel.SYS, "保存角色权限");
			} catch (Exception e) {
				//logService.error(Consts.SystemModel.SYS, "保存角色权限失败");
				throw new ServiceException("保存角色权限失败", e);
			}
		}
		return roleId;
	}

	@Override
	public Long getUserRoleCount(String[] ids) {
		try {
			return roleDao.queryUserRoleCount(ids);
		} catch (Exception e) {
			throw new ServiceException("查询角色关联用户失败", e);
		}
	}

	@Override
	public boolean checkExists(String name, String id) {
		long count = roleDao.checkExists(name.trim(), id);
		return count > 0;
	}

	@Override
	public void deleteByRoleId(String id) {
		if (StringUtils.isBlank(id)) {
			throw new ServiceException("参数为空.");
		}
		try {
			roleDao.deleteByRoleId(id);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "deleteByRoleId fail.");
			throw new ServiceException("删除错误", e);
		}

	}

}

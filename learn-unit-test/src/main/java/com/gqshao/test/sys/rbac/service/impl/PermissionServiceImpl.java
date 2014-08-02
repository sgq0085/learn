package com.gqshao.test.sys.rbac.service.impl;

import java.util.*;

import org.apache.shiro.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.gqshao.test.commons.exception.*;
import com.gqshao.test.commons.security.shiro.*;
import com.gqshao.test.commons.security.utils.*;
import com.gqshao.test.commons.utils.*;
import com.gqshao.test.sys.rbac.dao.*;
import com.gqshao.test.sys.rbac.domain.*;
import com.gqshao.test.sys.rbac.service.*;



@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public void save(Permission permission) {

		Long indexNo = permissionDao.queryMaxIndexNoByParentId(permission
				.getParentId());
		if (null == indexNo) {
			Long level = permission.getGrade();
			indexNo = Math.round(Math.pow(10, level.doubleValue()));
		}
		permission.setId(Ids.uuid());
		permission.setIndexNo(indexNo);
		permission.setCreateDatetime(new Date());
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		permission.setCreateUser(user.getId());
		permission.setIsDelete(0);

		try {
			permissionDao.save(permission);
			//logService.info(Consts.SystemModel.SYS, "添加角色 ");
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "保存权限失败");
			throw new ServiceException("保存权限失败", e);
		}
	}

	@Override
	public List<Permission> getPermissionsByRoleId(String roleId) {

		try {
			return permissionDao.queryByRoleId(roleId);
		} catch (Exception e) {

			//logService.error(Consts.SystemModel.SYS, "根据角色id查询权限失败");
			throw new ServiceException("根据角色id查询权限失败", e);

		}

	}

	@Override
	public List<Permission> getAllPermissions() {
		try {
			return permissionDao.queryAll();
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "查询权限失败");
			throw new ServiceException("查询权限失败", e);
		}

	}

	@Override
	public void update(Permission permission) {
		permission.setUpdateUser(Securitys.getUserId());
		permission.setUpdateDatetime(new Date());
		try {
			permissionDao.update(permission);
			//logService.info(Consts.SystemModel.SYS, "更新权限");
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "更新权限失败");
			throw new ServiceException("更新权限失败", e);
		}
	}

	

	@Override
	public void delete(String id) {

		try {
			permissionDao.delete(id);
			//logService.warning(Consts.SystemModel.SYS, "删除权限");
		} catch (Exception e) {

			//logService.error(Consts.SystemModel.SYS, "删除权限失败");
			throw new ServiceException("删除权限失败", e);
		}
	}

	@Override
	public List<Permission> getByUserId(String userId) {
		try {
			return permissionDao.queryByUserId(userId);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "根据用户id查询权限失败");
			throw new ServiceException("根据用户id查询权限失败", e);
		}
	}

	@Override
	public List<Permission> getByTenantId(String tenantId) {
		try {
			return permissionDao.query();
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "根据租户id查询权限失败");
			throw new ServiceException("根据租户id查询权限失败", e);
		}
	}

	@Override
	public Permission getByCode(String code, String userId) {
		try {
			return permissionDao.querByCodeAndUserId(code, userId);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "权限获取失败");
			throw new ServiceException("权限获取失败", e);
		}
	}

	@Override
	public Permission getByParentId(String parentId, String userId) {
		try {
			return permissionDao.querByParentIdAndUserId(parentId, userId);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "权限获取失败");
			throw new ServiceException("权限获取失败", e);
		}
	}


	@Override
	public Permission getByCodeAndTenantId(String code, String tenantId) {
		try {
			return permissionDao.queryByCode(code);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "权限获取失败");
			throw new ServiceException("权限获取失败", e);
		}
	}

	@Override
	public Permission getByParentId(String parentId) {
		try {
			Integer permissionType = 0;
			if (Securitys.isAdmin()) {
				permissionType = 1;
			}
			return permissionDao.querByParentId(parentId, permissionType);
		} catch (Exception e) {
			//logService.error(Consts.SystemModel.SYS, "权限获取失败");
			throw new ServiceException("权限获取失败", e);
		}
	}

}

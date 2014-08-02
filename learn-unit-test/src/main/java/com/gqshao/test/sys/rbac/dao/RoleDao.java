package com.gqshao.test.sys.rbac.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import com.gqshao.test.sys.rbac.domain.*;
import com.gqshao.test.sys.rbac.filter.*;

@Repository
public interface RoleDao {

	public void save(Role role);

	public Role queryById(@Param("id") String id);

	List<Role> queryByUserId(@Param("userId") String userId);

	List<Role> queryAll();

	List<Role> queryByFilter(RoleFilter filter);

	void update(Role role);

	void delete(String id);

	void deleteByIds(String[] ids);

	Integer queryMaxIndexNo();

	public void saveRolePermission(List<RolePermissionDTO> rpList);

	public void deleteRolePermission(String roleId);

	Long queryUserRoleCount(String[] ids);

	void deleteRolePermissions(String[] ids);

	long checkExists(@Param("name") String name, @Param("id") String id);


	void deleteByRoleId(String id);

}

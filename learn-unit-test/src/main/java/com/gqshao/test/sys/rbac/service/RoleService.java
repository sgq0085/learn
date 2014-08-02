package com.gqshao.test.sys.rbac.service;

import java.util.*;

import com.gqshao.test.sys.rbac.domain.*;
import com.gqshao.test.sys.rbac.filter.*;

/**
 * 
 * <pre>
 * 功能说明：角色Service interface
 * </pre>
 */
public interface RoleService {

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<Role> getAll();


	/**
	 * 按条件获取角色
	 * 
	 * @param filter
	 * @return
	 */
	public List<Role> getRolesByFilter(RoleFilter filter);

	/**
	 * 获取用户所有角色
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> getRolesByUserId(String userId);

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	public String save(Role role);

	/**
	 * 添加角色权限
	 * 
	 * @param role
	 * @param permissions
	 * @return
	 */
	public String save(Role role, String[] permissions);

	/**
	 * 根据id获取角色
	 * 
	 * @param roleId
	 * @return
	 */
	public Role getById(String roleId);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	public String update(Role role);

	/**
	 * 更新角色权限
	 * 
	 * @param role
	 * @param permissions
	 * @return
	 */
	public String update(Role role, String[] permissions);

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 */
	public void deleteByIds(String[] ids);

	/**
	 * 查询角色关联用户
	 * 
	 * @param ids
	 * @return
	 */
	Long getUserRoleCount(String[] ids);

	boolean checkExists(String name, String id);

	void deleteByRoleId(String id);

}

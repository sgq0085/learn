package com.gqshao.test.sys.rbac.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import com.gqshao.test.sys.rbac.domain.*;

@Repository
public interface PermissionDao {

	public void save(Permission permission);
	
	public Permission queryById(String id);
	
	public List<Permission> queryByRoleId(String roleId);
	
	public List<Permission> queryAll();
	
	public void update(Permission permission);
	
	public Long queryMaxIndexNoByParentId(String parentId);
	
	public void delete(String id);
	
	List<Permission> queryByUserId(String userId);
	
	List<Permission> query();

    public Permission querByCodeAndUserId(@Param("code")String code, @Param("userId")String userId);
    
    public Permission queryByCode(@Param("code")String code);
    
    public Permission querByParentIdAndUserId(@Param("parentId")String parentId, @Param("userId")String userId);

    public Permission querByParentId(@Param("parentId")String parentId,@Param("permissionType")Integer permissionType);

}

package com.gqshao.test.sys.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gqshao.test.sys.rbac.domain.User;
import com.gqshao.test.sys.rbac.domain.UserDTO;
import com.gqshao.test.sys.rbac.domain.UserRoleDTO;
import com.gqshao.test.sys.rbac.filter.UserFilter;

@Repository
public interface UserDao{
    
    public User findUserById(String id);

    public User findUserByLoginName(@Param("loginName") String loginName);

    List<UserDTO> list(UserFilter filter);

    public void save(User user);

    public void update(User user);
    
    void delete(String [] ids);

    public void saveUserRole(List<UserRoleDTO> urList);
    
    void deleteUserRole(String userId);
    
    void deleteUserRoles(String[] ids);

}

package com.gqshao.test.sys.rbac.domain;

/**
 * <pre>
 * 功能说明：用户角色关系
 * </pre>
 */
public class UserRoleDTO {
    
    private String userId;

    private String roleId;
    
    public UserRoleDTO(){}
    
    public UserRoleDTO(String userId,String roleId){
        
        this.userId = userId;
        
        this.roleId = roleId;
        
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}
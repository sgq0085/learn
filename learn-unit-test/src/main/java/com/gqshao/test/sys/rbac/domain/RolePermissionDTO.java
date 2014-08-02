package com.gqshao.test.sys.rbac.domain;

/**
 * <pre>
 * 功能说明：角色权限DTO
 * </pre>
 */
public class RolePermissionDTO {

    private String roleId;
    
    private String permissionId;

    public RolePermissionDTO() {}

    public RolePermissionDTO(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    
}

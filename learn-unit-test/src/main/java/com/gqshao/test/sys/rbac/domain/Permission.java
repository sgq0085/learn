package com.gqshao.test.sys.rbac.domain;

import java.util.Date;

public class Permission implements java.io.Serializable {
    
    private static final long serialVersionUID = -1962216132015549160L;
    
    // 主键ID
    private String id;
    // 权限名称
    private String name;
    // 权限代码
    private String code;
    // 父权限ID
    private String parentId;
    // 菜单级别
    private Long grade;
    // 排序号
    private Long indexNo;
    // 是否叶节点
    private Integer isLeaf;
    // URL路径
    private String url;
    // 创建人
    private String createUser;
    // 创建时间
    private Date createDatetime;
    // 更新人
    private String updateUser;
    // 更新时间
    private Date updateDatetime;
    // 备注
    private String remark;
    // 已删除
    private Integer isDelete;
    // 权限类型
    private Integer permissionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public Long getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Long indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

}
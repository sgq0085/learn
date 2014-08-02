package com.gqshao.test.sys.rbac.domain;

import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class User implements java.io.Serializable {

    private static final long serialVersionUID = 8672954738537279950L;

    private String id;
    // 登陆名
    @NotBlank
    @Length(max = 30)
    private String loginName;
    // 密码
    @Length(max = 30)
    private String password;
    // 盐
    private String salt;
    // 用户名
    @Length(max = 30)
    private String name;
    // Email
    @Email
    private String email;
    // 是否是超级管理员 0非 1是
    private Integer isAdmin;
    // 状态 0:禁用 1:启用
    private Integer status;
    // 创建人
    private String createUser;
    // 创建日期
    private Date createDatetime;
    // 修改人
    private String updateUser;
    // 修改日期
    private Date updateDatetime;
    // 备注
    private String remark;
    // 是否删除 0:未删除 1:已删除
    private Integer isDelete;

    public User() {
        super();
    }

    public User(String id, String loginName, String password, String name, String salt, String email,
            Integer isAdmin, Integer status, String createUser, Date createDatetime, String updateUser,
            Date updateDatetime, String remark, Integer isDelete) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.name = name;
        this.salt = salt;
        this.email = email;
        this.isAdmin = isAdmin;
        this.status = status;
        this.createUser = createUser;
        this.createDatetime = createDatetime;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
        this.remark = remark;
        this.isDelete = isDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
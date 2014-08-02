---- 创建报告表-------
CREATE TABLE sys_user (
  ID              CHAR(32) NOT NULL,
  login_name      VARCHAR2(50),
  password        VARCHAR2(50),
  name            VARCHAR2(50),
  salt            VARCHAR2(50),
  email           VARCHAR2(100),
  IS_ADMIN        NUMBER(1),
  STATUS          NUMBER(2),
  CREATE_USER     VARCHAR2(50),
  CREATE_DATETIME DATE,
  UPDATE_USER     VARCHAR2(50),
  UPDATE_DATETIME DATE,
  REMARK          VARCHAR2(500),
  IS_DELETE       NUMBER(1),
  CONSTRAINT PK_SYS_USER PRIMARY KEY (ID)
);

-- Add comments to the table 
COMMENT ON TABLE SYS_USER
IS '用户表';
-- Add comments to the columns 
COMMENT ON COLUMN SYS_USER.id
IS '主键ID';
COMMENT ON COLUMN SYS_USER.login_name
IS '登录名';
COMMENT ON COLUMN SYS_USER.password
IS '密码';
COMMENT ON COLUMN SYS_USER.name
IS '用户名称';
COMMENT ON COLUMN SYS_USER.salt
IS '盐';
COMMENT ON COLUMN SYS_USER.email
IS '邮件';
COMMENT ON COLUMN SYS_USER.is_admin
IS '是否超级管理员';
COMMENT ON COLUMN SYS_USER.status
IS '状态';
COMMENT ON COLUMN SYS_USER.create_user
IS '记录创建人';
COMMENT ON COLUMN SYS_USER.create_datetime
IS '记录创建时间';
COMMENT ON COLUMN SYS_USER.update_user
IS '记录更新人';
COMMENT ON COLUMN SYS_USER.update_datetime
IS '记录更新时间';
COMMENT ON COLUMN SYS_USER.remark
IS '备注';
COMMENT ON COLUMN SYS_USER.is_delete
IS '已删除';

------创建角色表-----------
CREATE TABLE SYS_ROLE
(
  id              CHAR(32) NOT NULL,
  name            VARCHAR2(50),
  index_no        NUMBER(4),
  create_user     VARCHAR2(50),
  create_datetime DATE,
  update_user     VARCHAR2(50),
  update_datetime DATE,
  remark          VARCHAR2(500),
  is_delete       NUMBER(1),
  CONSTRAINT PK_SYS_ROLE PRIMARY KEY (ID)
);
-- Add comments to the table 
COMMENT ON TABLE SYS_ROLE
IS '角色表';
-- Add comments to the columns 
COMMENT ON COLUMN SYS_ROLE.id
IS '主键ID';
COMMENT ON COLUMN SYS_ROLE.name
IS '角色名称';
COMMENT ON COLUMN SYS_ROLE.index_no
IS '序号';
COMMENT ON COLUMN SYS_ROLE.create_user
IS '记录创建人';
COMMENT ON COLUMN SYS_ROLE.create_datetime
IS '记录创建时间';
COMMENT ON COLUMN SYS_ROLE.update_user
IS '记录更新人';
COMMENT ON COLUMN SYS_ROLE.update_datetime
IS '记录更新时间';
COMMENT ON COLUMN SYS_ROLE.remark
IS '备注';
COMMENT ON COLUMN SYS_ROLE.is_delete
IS '已删除';

------用户角色关系表-----------
CREATE TABLE SYS_USER_ROLE
(
  user_id CHAR(32) NOT NULL,
  role_id CHAR(32) NOT NULL,
  CONSTRAINT PK_SYS_USER_ROLE PRIMARY KEY (USER_ID, ROLE_ID)
);
-- Add comments to the table 
COMMENT ON TABLE SYS_USER_ROLE
IS '用户角色关系表';
-- Add comments to the columns 
COMMENT ON COLUMN SYS_USER_ROLE.user_id
IS '用户ID';
COMMENT ON COLUMN SYS_USER_ROLE.role_id
IS '角色ID';

ALTER TABLE SYS_USER_ROLE
ADD CONSTRAINT FK_SYS_USER_REFERENCE_SYS_USER FOREIGN KEY (USER_ID)
REFERENCES SYS_USER (ID);
ALTER TABLE SYS_USER_ROLE
ADD CONSTRAINT fk_sys_role_reference_sys_role FOREIGN KEY (role_id)
REFERENCES sys_role (id);

------创建权限表----------
CREATE TABLE SYS_PERMISSION
(
  id              CHAR(32) NOT NULL,
  name            VARCHAR2(50),
  code            VARCHAR2(100),
  parent_id       VARCHAR2(50),
  grade           NUMBER(16),
  index_no        NUMBER(16),
  is_leaf         NUMBER(1),
  url             VARCHAR2(500),
  module_id       CHAR(32),
  create_user     VARCHAR2(50),
  create_datetime DATE,
  update_user     VARCHAR2(50),
  update_datetime DATE,
  remark          VARCHAR2(500),
  is_delete       NUMBER(1),
  permission_type NUMBER(2),
  CONSTRAINT PK_SYS_PERMISSION PRIMARY KEY (ID)
);
-- Add comments to the table 
COMMENT ON TABLE SYS_PERMISSION
IS '权限表';
-- Add comments to the columns 
COMMENT ON COLUMN SYS_PERMISSION.id
IS '主键ID';
COMMENT ON COLUMN SYS_PERMISSION.name
IS '权限码名称';
COMMENT ON COLUMN SYS_PERMISSION.code
IS '权限码代码';
COMMENT ON COLUMN SYS_PERMISSION.parent_id
IS '父节点ID';
COMMENT ON COLUMN SYS_PERMISSION.grade
IS '菜单级别';
COMMENT ON COLUMN SYS_PERMISSION.index_no
IS '排序号';
COMMENT ON COLUMN SYS_PERMISSION.is_leaf
IS '是否叶节点';
COMMENT ON COLUMN SYS_PERMISSION.url
IS 'URL路径';
COMMENT ON COLUMN SYS_PERMISSION.create_user
IS '记录创建人';
COMMENT ON COLUMN SYS_PERMISSION.create_datetime
IS '记录创建时间';
COMMENT ON COLUMN SYS_PERMISSION.update_user
IS '记录更新人';
COMMENT ON COLUMN SYS_PERMISSION.update_datetime
IS '记录更新时间';
COMMENT ON COLUMN SYS_PERMISSION.remark
IS '备注';
COMMENT ON COLUMN SYS_PERMISSION.is_delete
IS '已删除';
COMMENT ON COLUMN SYS_PERMISSION.permission_type
IS '权限类型';


-----创建角色权限关系表--------------
CREATE TABLE SYS_ROLE_PERMISSION
(
  role_id        CHAR(32) NOT NULL,
  permissions_id CHAR(32) NOT NULL,
  CONSTRAINT PK_SYS_ROLE_PERMISSION PRIMARY KEY (ROLE_ID, PERMISSIONS_ID)
);
-- Add comments to the table 
COMMENT ON TABLE SYS_ROLE_PERMISSION
IS '角色权限关系表';
-- Add comments to the columns 
COMMENT ON COLUMN SYS_ROLE_PERMISSION.role_id
IS '角色ID';
COMMENT ON COLUMN SYS_ROLE_PERMISSION.permissions_id
IS '权限ID';
-----添加外键-------------
ALTER TABLE SYS_ROLE_PERMISSION
ADD CONSTRAINT FK_RP_REFERENCE_SYS_PERMISSION FOREIGN KEY (permissions_id)
REFERENCES SYS_PERMISSION (ID);
ALTER TABLE SYS_ROLE_PERMISSION
ADD CONSTRAINT FK_RP_REFERENCE_SYS_ROLE FOREIGN KEY (role_id)
REFERENCES SYS_ROLE (ID);

-- 创建用户表
CREATE TABLE sys_user (
  id              CHAR(32) NOT NULL,
  login_name      VARCHAR(50),
  password        VARCHAR(50),
  name            VARCHAR(50),
  salt            VARCHAR(50),
  email           VARCHAR(100),
  is_admin        INT(1),
  status          INT(2),
  create_user     VARCHAR(50),
  create_datetime DATETIME,
  update_user     VARCHAR(50),
  update_datetime DATETIME,
  remark          VARCHAR(500),
  is_delete       int(1),
  PRIMARY KEY (id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 创建角色表
CREATE TABLE sys_role
(
  id              CHAR(32) NOT NULL,
  name            VARCHAR(50),
  index_no        INT(4),
  create_user     VARCHAR(50),
  create_datetime DATETIME,
  update_user     VARCHAR(50),
  update_datetime DATETIME,
  remark          VARCHAR(500),
  is_delete       INT(1),
  PRIMARY KEY (ID)
)   ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sys_user_role
(
  user_id CHAR(32) NOT NULL,
  role_id CHAR(32) NOT NULL,
  PRIMARY KEY (USER_ID, ROLE_ID)
)   ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建权限表
CREATE TABLE sys_permission
(
  id              CHAR(32) NOT NULL,
  name            VARCHAR(50),
  code            VARCHAR(100),
  parent_id       VARCHAR(50),
  grade           INT(16),
  index_no        INT(16),
  is_leaf         INT(1),
  url             VARCHAR(500),
  module_id       CHAR(32),
  create_user     VARCHAR(50),
  create_datetime DATETIME,
  update_user     VARCHAR(50),
  update_datetime DATETIME,
  remark          VARCHAR(500),
  is_delete       INT(1),
  permission_type INT(2),
  CONSTRAINT PK_SYS_PERMISSION PRIMARY KEY (ID)
)   ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 创建角色权限关系表
CREATE TABLE sys_role_permission
(
  role_id        CHAR(32) NOT NULL,
  permissions_id CHAR(32) NOT NULL,
  PRIMARY KEY (ROLE_ID, PERMISSIONS_ID)
)   ENGINE=InnoDB DEFAULT CHARSET=utf8;


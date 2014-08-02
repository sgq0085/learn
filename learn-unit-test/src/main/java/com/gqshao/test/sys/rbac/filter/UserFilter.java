package com.gqshao.test.sys.rbac.filter;

import com.gqshao.test.commons.persistence.domain.*;

public class UserFilter extends BaseFilter {

    private String loginName;

    private String name;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

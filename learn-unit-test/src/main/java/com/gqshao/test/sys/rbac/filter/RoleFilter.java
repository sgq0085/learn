package com.gqshao.test.sys.rbac.filter;

import com.gqshao.test.commons.persistence.domain.*;

public class RoleFilter extends BaseFilter{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

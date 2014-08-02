package com.gqshao.myapp.demo.po;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.gqshao.myapp.common.persistence.IdEntity;
/**
 * 该对象只保存到数据源2中, 放在po包下是为了方便区分
 create table jta_pu2 (  
    id char(32),  
    description varchar2(255),  
    primary key (id)  
 );  
 */
@Entity
@Table(name = "JTA_PU2")
public class PU2 extends IdEntity {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
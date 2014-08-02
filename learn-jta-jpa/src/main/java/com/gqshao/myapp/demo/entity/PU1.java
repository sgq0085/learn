package com.gqshao.myapp.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.gqshao.myapp.common.persistence.BaseEntity;
/**
 * 该对象同时保存到数据源1和数据源2中
 create table jta_pu1 (  
    id char(32),  
    description varchar2(255),  
    primary key (id)  
 );  
 */
@Entity
@Table(name = "JTA_PU1")
public class PU1 extends BaseEntity {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
package com.gqshao.redis.domain;

import java.io.Serializable;
import java.util.Date;

public class SerializableBean implements Serializable {
    public String id;
    public String name;
    public String value;
    public Date date;
    public Boolean isTrue;


    public SerializableBean() {
    }

    public SerializableBean(String id, String name, String value, Date date, Boolean isTrue) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.date = date;
        this.isTrue = isTrue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Boolean isTrue) {
        this.isTrue = isTrue;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}

/**
 * TestBean.java Create on 2014/12/12
 * Copyright(c) Gener-Tech Inc.
 * ALL Rights Reserved.
 */
package com.gqshao.redis.domin;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author <a href="mailto:shao.gq@gener-tech.com">shaogq</a>
 * @version 1.0
 */
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

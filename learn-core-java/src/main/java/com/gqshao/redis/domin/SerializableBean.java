package com.gqshao.redis.domin;

import java.io.Serializable;
import java.util.Date;


public class SerializableBean extends BaseBean implements Serializable {
    private String value;
    public Date date;
    public Boolean isTrue;


    public SerializableBean() {
    }

    public SerializableBean(String id, String name, String value, Date date, Boolean isTrue) {
        super.setId(id);
        super.setName(name);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}

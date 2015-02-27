package com.gqshao.mongo.domain;

import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Property;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@org.mongodb.morphia.annotations.Entity(value = "MorphiaBean", noClassnameStored = true)
public class MorphiaBean implements Serializable {
    public String id;

    // 字段可以被加载，但不被保存
    @NotSaved
    public String name;

    // 字段被忽略(不加载/保存)
    @org.mongodb.morphia.annotations.Transient
    public String value;

    public String val;

    // 字段会被修改保存key
    @Property("started")
    public Date date;
    public Boolean isTrue;


    public MorphiaBean() {
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

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public static MorphiaBean newInstance() {
        MorphiaBean bean = new MorphiaBean();
        bean.setId(UUID.randomUUID().toString());
        bean.setName("bean" + ((int) Math.random() * 100));
        bean.setValue("value" + ((int) Math.random() * 100));
        bean.setVal("not changed");
        bean.setDate(DateTime.now().toDate());
        bean.setIsTrue(Math.random() > 0.5);
        return bean;
    }

}

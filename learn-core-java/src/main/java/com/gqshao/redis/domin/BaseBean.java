package com.gqshao.redis.domin;

/**
 * 没有继承Serializable接口 属性不会被持久化
 */
public class BaseBean {
    private String id;
    private String name;

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
}

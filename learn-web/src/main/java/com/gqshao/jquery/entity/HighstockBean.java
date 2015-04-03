package com.gqshao.jquery.entity;

import java.util.Date;
import java.util.UUID;

public class HighstockBean {
    private UUID id;
    private String name;
    private Date x;
    private Double y;

    public HighstockBean() {
    }

    public HighstockBean(UUID id, String name, Date x, Double y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getX() {
        return x;
    }

    public void setX(Date x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
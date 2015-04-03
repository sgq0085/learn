package com.gqshao.script;

import java.util.Date;

public class Pojo {
    private String str;
    private Integer inte;
    private Boolean bool;
    private Date date;

    public Pojo(String str, Integer inte, Boolean bool, Date date) {
        this.str = str;
        this.inte = inte;
        this.bool = bool;
        this.date = date;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getInte() {
        return inte;
    }

    public void setInte(Integer inte) {
        this.inte = inte;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
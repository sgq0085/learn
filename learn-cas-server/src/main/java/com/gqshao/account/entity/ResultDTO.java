package com.gqshao.account.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResultDTO")
public class ResultDTO {
    private Boolean success;
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}


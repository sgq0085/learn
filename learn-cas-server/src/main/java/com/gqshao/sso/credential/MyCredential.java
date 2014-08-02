package com.gqshao.sso.credential;

import org.jasig.cas.authentication.Credential;

import java.io.Serializable;

public class MyCredential implements Credential, Serializable {

    private static final long serialVersionUID = -700605081472810939L;

    private String username;

    private String password;

    private String custom;

    public MyCredential() {
    }

    public MyCredential(final String userName, final String password) {
        this.username = userName;
        this.password = password;
    }

    public MyCredential(String username, String password, String custom) {
        this.username = username;
        this.password = password;
        this.custom = custom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Override
    public String getId() {
        return this.username;
    }

    @Override
    public String toString() {
        return this.username + this.password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyCredential that = (MyCredential) o;

        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }

        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
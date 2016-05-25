package com.gqshao.mail.domain;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * javamail自定义授权
 */
public class MailAuthenticatorBak extends Authenticator {
    private String username;
    private String password;

    public MailAuthenticatorBak() {
    }

    public MailAuthenticatorBak(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}

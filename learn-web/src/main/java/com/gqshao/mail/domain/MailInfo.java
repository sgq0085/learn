package com.gqshao.mail.domain;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

/**
 * javamail自定义封装
 */
public class MailInfo {
    // 服务器ip
    private String mailServerHost;
    // 端口
    private String mailServerPort;
    // 发送者的邮件地址
    private String fromAddress;
    // 邮件接收者地址
    private String toAddress;
    // 登录邮件发送服务器的用户名
    private String username;
    // 登录邮件发送服务器的密码
    private String password;
    // 是否需要身份验证
    private boolean validate = false;
    // 邮件主题
    private String subject;
    // 邮件内容
    private String content;
    // 附件名称
    private String[] attachFileNames;

    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
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

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }

    public MailAuthenticator getMailAuthenticator() {
        return new MailAuthenticator(this.username, this.password);
    }

    public class MailAuthenticator extends Authenticator {
        private String username;
        private String password;

        public MailAuthenticator() {
        }

        public MailAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

}

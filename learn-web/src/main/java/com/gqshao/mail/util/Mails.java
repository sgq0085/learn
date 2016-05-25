package com.gqshao.mail.util;

import com.gqshao.mail.domain.MailInfo;
import org.joda.time.DateTime;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;

/**
 * JavaMail自定义工具类
 */
public class Mails {

    // 以文本格式发送邮件
    public static boolean sendTextMail(MailInfo mailInfo) {
        try {
            // 构造一个发送邮件的session
            Session sendMailSession = getSession(mailInfo);
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 设置邮件消息的接收者
            setRecipient(mailMessage, mailInfo.getToAddress());
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(DateTime.now().toDate());
            // 设置邮件消息的主要内容
            mailMessage.setText(mailInfo.getContent());
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 以HTML格式发送邮件
    public static boolean sendHtmlMail(MailInfo mailInfo) {
        try {
            // 构造一个发送邮件的session
            Session sendMailSession = getSession(mailInfo);
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            setRecipient(mailMessage, mailInfo.getToAddress());
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(DateTime.now().toDate());

            // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // 设置HTML内容
            messageBodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(messageBodyPart);

            // 存在附件
            String[] filePaths = mailInfo.getAttachFileNames();
            if (filePaths != null && filePaths.length > 0) {
                for (String filePath : filePaths) {
                    messageBodyPart = new MimeBodyPart();
                    File file = new File(filePath);
                    // 附件存在磁盘中
                    if (file.exists()) {
                        // 得到数据源
                        FileDataSource fds = new FileDataSource(file);
                        // 得到附件本身并至入BodyPart
                        messageBodyPart.setDataHandler(new DataHandler(fds));
                        // 得到文件名同样至入BodyPart
                        messageBodyPart.setFileName((MimeUtility.encodeWord(file.getName())));
                        mainPart.addBodyPart(messageBodyPart);
                    }
                }
            }

            // 将MimeMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void setRecipient(Message mailMessage, String toAddress) throws MessagingException {
        String[] toAddressArray = toAddress.split(";");
        if (1 == toAddressArray.length) {
            // 创建邮件的接收者地址
            Address to = new InternetAddress(toAddress);
            // 设置邮件消息的接收者
            mailMessage.setRecipient(Message.RecipientType.TO, to);
        } else {
            Address[] to = new InternetAddress[toAddressArray.length];
            for (int i = 0; i < toAddressArray.length; i++) {
                to[i] = new InternetAddress(toAddressArray[i]);
            }
            mailMessage.setRecipients(Message.RecipientType.TO, to);
        }
    }

    private static Session getSession(MailInfo mailInfo) {
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = null;
        // 判断是否需要身份认证
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            sendMailSession = Session.getDefaultInstance(mailInfo.getProperties(), mailInfo.getMailAuthenticator());
        } else {
            sendMailSession = Session.getDefaultInstance(mailInfo.getProperties());
        }
        return sendMailSession;
    }
}

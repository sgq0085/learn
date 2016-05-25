package com.gqshao.mail;

import com.gqshao.mail.domain.MailInfo;
import com.gqshao.mail.util.Mails;
import org.junit.Test;

public class MailTest {

    /**
     * 经测试
     * 图片是唯一可以引用的外部资源。其他的外部资源，比如样式表文件、字体文件、视频文件等，一概不能引用。
     */
    @Test
    public void send() {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUsername("xxxxxx");
        mailInfo.setPassword("xxxxxx");
        mailInfo.setFromAddress("xxxxxx");
        mailInfo.setToAddress("xxxxxx;xxxxxx");
        mailInfo.setSubject("邮箱标题");
        mailInfo.setContent("邮箱内容");
        // 发送普通Email
        Mails.sendTextMail(mailInfo);
        //附件
        String[] attachFileNames = {"C:\\Users\\shaoguoqing\\Desktop\\附件1.jpg"};
        mailInfo.setAttachFileNames(attachFileNames);

        // 这个类主要来发送邮件
        mailInfo.setSubject("邮箱标题0525b");
        StringBuffer demo = new StringBuffer();
        demo.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")
                .append("<html>")
                .append("<head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
                .append("<title>测试邮件</title>")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://mmp.m.jd.com/static/bootstrap/bootstrap-3.3.5/css/bootstrap.min.css\"/>")
                .append("<script type=\"text/javascript\" src=\"http://mmp.m.jd.com/static/jquery/jquery-1.11.3/jquery-1.11.3.min.js\"></script>")
                .append("<style type=\"text/css\">")
                .append(".test{font-family:\"Microsoft Yahei\";font-size: 18px;color: red;}")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<span class=\"test\">大家好，这里是测试Demo</span>")
                .append("</body>")
                .append("</html>");

        mailInfo.setContent(demo.toString());
        // 发送html格式
        Mails.sendHtmlMail(mailInfo);
    }
}

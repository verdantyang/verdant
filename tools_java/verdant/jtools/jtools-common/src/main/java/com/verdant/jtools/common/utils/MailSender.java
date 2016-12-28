package com.verdant.jtools.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件发送工具
 *
 * @author verdant
 * @since 2016/11/23
 */
public class MailSender {

    private static Logger logger = LoggerFactory.getLogger(MailSender.class);
    // 服务器配置信息
    private static final String PROP_SMTP = "mail.smtp.host";
    private static final String PROP_AUTH = "mail.smtp.auth";

    //默认信息
    private static final String DEFAULT_SMTP = "smtp.163.com";
    private static final String DEFAULT_FROM = "caocao_alarm@163.com";
    private static final String DEFAULT_UNAME = "caocao_alarm";
    private static final String DEFAULT_PASSWD = "1qaz2wsx";

    private MimeMessage message;
    private Session session;

    public MailSender() {
        this(DEFAULT_SMTP, DEFAULT_UNAME, DEFAULT_PASSWD);
    }

    /*
     * 构造函数
     */
    public MailSender(String smtp, final String userName, final String passWord) {
        Properties props = System.getProperties();
        props.setProperty(PROP_SMTP, smtp);
        props.setProperty(PROP_AUTH, "true");
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, passWord);
            }
        });
        session.setDebug(true);
        message = new MimeMessage(session);
    }

    /**
     * 发送邮件
     *
     * @param mailTitle 邮件头文件名
     * @param mailBody  邮件内容
     * @param fromEmail 发件人地址
     * @param toEmail   收件人地址
     */
    public void sendEmail(String mailTitle, String mailBody, String fromEmail, String toEmail) {
        try {
            //发件人和收件人
            InternetAddress from = new InternetAddress(fromEmail);
            message.setFrom(from);
            InternetAddress to = new InternetAddress(toEmail);
            message.setRecipient(Message.RecipientType.TO, to);
            // 邮件标题
            message.setSubject(mailTitle);
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(mailBody, "text/html;charset=GBK");
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            // smtp验证
            transport.connect(DEFAULT_SMTP, DEFAULT_UNAME, DEFAULT_PASSWD);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            logger.info("send email success!");
        } catch (MessagingException e) {
            logger.error("send email failed, reason {}", e);
        }
    }

    public static void main(String[] args) {
        MailSender mailSender = new MailSender();
        mailSender.sendEmail("测试邮件", "邮件内容", DEFAULT_FROM, "congyu.yang@geely.com");
    }
}

package com.jtools.common.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class MailUtils {
	public static void main(String[] args) throws Exception{
		Authenticator a=new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("1009980411@qq.com", "*******");
			}
		};
		Properties p=new Properties();
		p.put("mail.smtp.host","smtp.qq.com");
	    p.put("mail.smtp.port",25);    
	    p.put("mail.smtp.auth", "true");    
		Session session=Session.getDefaultInstance(p, a);
		// 根据session创建一个邮件消息    
	      Message mailMessage = new MimeMessage(session);    
	      // 创建邮件发送者地址    
	      Address from = new InternetAddress("1009980411@qq.com");    
	      // 设置邮件消息的发送者    
	      mailMessage.setFrom(from);    
	      // 创建邮件的接收者地址，并设置到邮件消息中    
	      Address to = new InternetAddress("2428342926@qq.com");   
	      // "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址  
	      mailMessage.setRecipient(Message.RecipientType.TO,to);    
	      // 设置邮件消息的主题    
	      mailMessage.setSubject("易象软件有限公司--员工通知");    
	      // 设置邮件消息发送的时间    
	      mailMessage.setSentDate(new Date());   
	      
	      /**
	       * HTML内容发送
	       */
	      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
	      Multipart mainPart = new MimeMultipart();    
	      // 创建一个包含HTML内容的MimeBodyPart    
	      BodyPart html = new MimeBodyPart();    
	      // 设置HTML内容    
	      html.setContent("Html内容发送 <br><img src='http://img0.pcgames.com.cn/pcgames/1211/06/2677681_a9_thumb.jpg'>", "text/html; charset=utf-8");    
	      mainPart.addBodyPart(html);    
	      // 将MiniMultipart对象设置为邮件内容    
	      mailMessage.setContent(mainPart);    
	      
	      
	      /**
	       * 纯文本内容发送
	       */
	      // 设置邮件消息的主要内容    
//	      String mailContent = "Dota么亲！";    
//	      mailMessage.setText(mailContent);    
	      // 发送邮件    
	      Transport.send(mailMessage);   
	}
}

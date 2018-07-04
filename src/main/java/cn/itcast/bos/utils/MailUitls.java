package cn.itcast.bos.utils;

import java.util.Properties;  

import javax.mail.Authenticator;  
import javax.mail.Message;  
import javax.mail.Message.RecipientType;  
import javax.mail.MessagingException;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.AddressException;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
public class MailUitls {  
    public static void sendMail(String to,String code){  
          
        /** 
         * 1.获取session 
         * 2.创建一个代码邮件的对象message 
         * 3.发送邮件Transport 
         */  
        /** 
         * 1.获得连接对象 
         */  
        Properties props=new Properties();  
        props.setProperty("mail.host","localhost");  
        Session session=Session.getDefaultInstance(props, new Authenticator(){  
  
            @Override  
            protected PasswordAuthentication getPasswordAuthentication() {  
                // TODO Auto-generated method stub  
                return new PasswordAuthentication("lisi@jc.com","123456");  
            }  
              
        });  
        //2.创建邮件发送对象  
        Message message=new MimeMessage(session);  
        //3.设置发件人  
        try {  
            message.setFrom(new InternetAddress("lisi@jc.com"));  
            //设置收件人  
            message.addRecipient(RecipientType.TO, new InternetAddress(to));  
            //标题  
            message.setSubject("来自滨海学院校。趣快递的官网邮件");  
            message.setContent("<h1>滨海学院校。趣快递的官网!点下面链接完成跳转!</h1><h3><a href='http://192.168.0.7'>http://192.168.0.7</a></h3></br>"+code+"", "text/html;charset=UTF-8");  
            // 3.发送邮件:  
            Transport.send(message);  
        } catch (AddressException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (MessagingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
      
    public static void main(String[] args) {  
        sendMail("zhangsan@jc.com","你的激活码是azz19931016");  
    }  
}  
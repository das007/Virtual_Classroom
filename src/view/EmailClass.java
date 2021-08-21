/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class EmailClass 
{
    String toEmail,fromEmail,fromEmailPwd,strText,strSubject;
    
    String port,smtpServer;
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    
    Session s;

    public EmailClass() 
    {
        getEmailInfo();
        setServer();
    }
    
    private void setServer()
    {
        try
        {
            if(fromEmail.endsWith("gmail.com"))
            {
                smtpServer="smtp.gmail.com";
                port="587";
            }
            else if(fromEmail.endsWith("hotmail.com"))
            {
                smtpServer="smtp.live.com";
                port="587";
            }
            else if(fromEmail.endsWith("yahoo.com"))
            {
                smtpServer="smtp.mail.yahoo.com";
                port="587";
            }
            
            Properties p=new Properties();
            p.put("mail.smtp.host",smtpServer);
       //     p.put("mail.smtp.socketFactory.port",port);
      //      p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.port",port);
            p.put("mail.smtp.starttls.enable",true);

            s=Session.getDefaultInstance(p,new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(fromEmail,fromEmailPwd);
                }
            });
            s.setDebug(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in setServer="+e);
        }
    }
    
    public void sendEmail(String pptEmail[],String subject,String message)
    {
        try
        {
            toEmail="";
            for(int i=0;i<pptEmail.length-1;i++)
            {
                toEmail+=pptEmail[i]+",";
            }
            toEmail+=pptEmail[pptEmail.length-1];
            
            MimeMessage msg=new MimeMessage(s);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        }
        catch(Exception e)
        {
            System.out.println("Excep in sendEmail="+e);
        }
    }
    
    private void getEmailInfo()
    {
        try
        {
            //select emailpwd from UserInfo where Unm="sdf";
            String colnm[]={"Email_id","Email_pwd"};
            String condtn[]={"User_name"};
            String val[]={GenClass.username};
            ArrayList result=db.select("UserInfo", colnm, condtn, val, null);
            
            ArrayList userEmail=(ArrayList)result.get(0);
            fromEmail=userEmail.get(0).toString();
            fromEmailPwd=userEmail.get(1).toString();
        }
        catch(Exception e)
        {
            System.out.println("Excep in getEmailPwd="+e);
        }
    }
    
}

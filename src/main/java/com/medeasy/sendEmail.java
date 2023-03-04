package com.medeasy;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Transport;
public class sendEmail {

    public static void sendMail (String recepient,String subject, String content) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "noreply.medeasy@gmail.com";
        String password = "njqhcclbqvlllzjv";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session,myAccountEmail,recepient,subject,content);
        Transport.send(message);
    }
    private static Message prepareMessage(Session session,String myAccountEmail,String recepient,String subject, String content) throws Exception {
        try {

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
        message.setSubject(subject);
        message.setContent(content,"text/html");
        return message;
        }
        catch (Exception e)
        {

        }
        return null;
    }

    private String userName;
    private String code;
    String htmlCode = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><title></title><link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Rubik\"><style type=\"text/css\">@media screen{@font-face{font-family:Rubik;font-style:normal;font-weight:400;src:url(https://fonts.gstatic.com/s/rubik/v11/iJWZBXyIfDnIV5PNhY1KTN7Z-Yh-B4iFV0Uz.woff) format('woff')}}@media only screen and (max-width:620px){.wrapper .section{width:100%}.wrapper .column{width:100%;display:block}}</style></head><body style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"></p><table width=\"100%\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"wrapper\" width=\"600\" align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;padding-left:10px;padding-right:10px;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table class=\"section header\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:initial;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"column\" style=\"padding:0;margin:0;border:1px solid #c3cdc9;border-radius:8px;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;padding-top:64px\"><h2 style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:center;padding-top:32px;padding-bottom:3px\">One-Time Password (OTP)</h2><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;margin-bottom:48px\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;vertical-align:middle\"><h2 style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;padding:0\">&nbsp;&nbsp;MedEasy</h2></td></tr></tbody></table></td></tr><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"left\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;border-top:1px solid #c3cdc9;padding:46px 54px 64px\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-weight:600;text-align:left\">Hello, "+userName+"!</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:left\">Enter the following OTP to create a new account</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-weight:600;font-size:24px;text-align:center\">"+code+"</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:left\">Do not forward or give this code to anyone.<br></p></td></tr></tbody></table></td></tr><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"column\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;width:100%;border-bottom:1px solid #c3cdc9\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-size:14px;padding-bottom:32px\">For any concerns, please reach out MedEasy support Team</p></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></body></html>";

}
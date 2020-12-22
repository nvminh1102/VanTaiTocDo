package com.osp.common;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendMailUtil {
    private static final Logger logger = LoggerFactory.getLogger(SendMailUtil.class);

    public static void sendGmail(ArrayList<String> to,ArrayList<String>bcc,ArrayList<String>cc, String subject, String body) throws AddressException, MessagingException {
        String from = SystemProperties.getProperty("email.username");
        String pass = SystemProperties.getProperty("email.password");
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        InternetAddress[] toAddress = new InternetAddress[to.size()];
        InternetAddress[] toAddressBcc = new InternetAddress[bcc.size()];
        InternetAddress[] toAddressCC = new InternetAddress[cc.size()];
        // To get the array of addresses
        for (int i = 0; i < to.size(); i++) {
            toAddress[i] = new InternetAddress(to.get(i));
        }

        for (int i = 0; i < toAddress.length; i++) {
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }
        for (int i = 0; i < bcc.size(); i++) {
            toAddressBcc[i] = new InternetAddress(bcc.get(i));
        }

        for (int i = 0; i < toAddressBcc.length; i++) {
            message.addRecipient(Message.RecipientType.BCC, toAddressBcc[i]);
        }
         for (int i = 0; i < cc.size(); i++) {
            toAddress[i] = new InternetAddress(cc.get(i));
        }

        for (int i = 0; i < toAddressCC.length; i++) {
            message.addRecipient(Message.RecipientType.CC, toAddressCC[i]);
        }
        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");
        message.setContent(body, "text/html; charset=UTF-8");
        message.setSubject(subject, "UTF-8");
        // message.setText(body, "UTF-8", "text/html");

        // //System.out.println(message.getContent().toString());
        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

   public static void main(String[] args) throws MessagingException {      
       ArrayList<String> arry=new ArrayList();
       
       arry.add("thiennv@osp.vn");
       ArrayList<String>arrBcc=new ArrayList<>();
       arrBcc.add("minhnv@osp.com.vn");
       arrBcc.add("huynv@osp.vn");
       sendGmail(arry,arrBcc,new ArrayList<String>(),"mailtest","<h1>Nguyễn Văn Thiên đang test mail nhé</h1>");
      
       
    }
}

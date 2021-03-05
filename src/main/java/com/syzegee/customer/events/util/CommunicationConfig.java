/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.syzegee.customer.events.exception.CustomerRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Sagar
 */
@Slf4j
@Component
public class CommunicationConfig {

    public boolean emailTemplateInfo(String template,String email) {
        log.info("Initiate emailTemplateConfig in CommunicationConfig");
        boolean isSuccess = false;
        Properties props = new Properties();
        String senderEmail = "girishathanikar54@gmail.com";
        String senderPassword = "7259667143$$##";
        try {
            InputStream is = CommunicationConfig.class.getResourceAsStream("/mail_settings.properties");
            props.load(is);
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));//provider defalut account
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email)); //To reciever mail id
            message.setSubject("Welcome to Szells Platform");
            message.setContent(template, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart(template);
            Transport.send(message);
            isSuccess = true;
        } catch (MessagingException | IOException e) {
            log.info("error in sending email ");
            log.error("stack trace>>  ",e);

            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "Exception while sending email");
        }
        log.info("end of emailTemplateConfig CommunicationConfig");
        return isSuccess;
    }
}

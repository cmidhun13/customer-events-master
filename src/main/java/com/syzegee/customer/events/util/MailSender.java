package com.syzegee.customer.events.util;

import com.syzegee.customer.events.exception.CustomerServiceException;
import com.syzegee.customer.events.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Sagar
 */
@Slf4j
@Component
public class MailSender {

    @Value("${mail.from}")
    private String from;
    @Value("${mail.fromName}")
    private String fromName;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.smtpUsername}")
    private String smtpUsername;
    @Value("${mail.smtpPassword}")
    private String smtpPassword;
    @Value("${mail.port}")
    private int port;
    @Value("${mail.subject}")
    private String subject;

    public boolean emailTemplateInfo(String template, String email) throws IOException {
        log.info("Initiate emailTemplateConfig in CommunicationConfig");
        boolean isSuccess = false;
        Properties props = new Properties();

        try {
            InputStream is = MailSender.class.getResourceAsStream("/mail_settings.properties");
            props.load(is);
            // Create a Session object to represent a mail session with the specified properties.
            Session session = Session.getDefaultInstance(props);
            // Create a message with the specified information.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, fromName));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject(subject);
            msg.setContent(template, "text/html; charset=utf-8");

            // Create a transport.
            Transport transport = session.getTransport();
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(host, smtpUsername, smtpPassword);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            isSuccess = true;
            log.info("Email sent!");
        } catch (MessagingException | IOException e) {
            log.info("error in sending email ");
            log.error("stack trace>>  ", e);
           
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Exception while sending email : Email Id is not validated");
        }
        log.info("end of emailTemplateConfig CommunicationConfig");
        return isSuccess;
    }
}


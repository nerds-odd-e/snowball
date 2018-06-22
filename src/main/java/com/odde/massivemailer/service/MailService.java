package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.List;
import java.util.Properties;

public interface MailService {
    String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";
    String SMTP_ADDR = "smtp.gmail.com";
    int SMTP_PORT = 587;
    int IMAP_PORT = 143;
    String IMAP_HOST ="imap.gmail.com" ;

    void send(Mail email) throws EmailException;

    MailService mailService = createMailService();

    static MailService createMailService() {
        if (mailService == null) {
            MailService ms = new MockMailService();
            if (!isMailServiceMocked()) {
                MailConfiguration config = new MailConfiguration(System.getenv("MM_EMAIL_USERID"),
                        System.getenv(EMAIL_PASSWORD),
                        ServerConfig.get(SMTP_ADDR,SMTP_PORT),
                        ServerConfig.get(IMAP_HOST,IMAP_PORT));
                final Session mailSession = createMailSession();
                ms = new GMailService(config, mailSession);
            }
            return ms;
        }
        return mailService;
    }

    static boolean isMailServiceMocked() {
        return "test".equals(System.getProperty("active_env"));
    }

    static Session createMailSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        return Session.getDefaultInstance(props);
    }

    List<Message> readEmail(boolean readFlag) throws MessagingException;
}

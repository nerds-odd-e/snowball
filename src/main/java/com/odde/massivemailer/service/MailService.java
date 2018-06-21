package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

import javax.mail.Session;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public interface MailService {
    String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";
    String SMTP_ADDR = "smtp.gmail.com";
    int PORT = 587;

    void send(Mail email) throws EmailException;

    Set<Mail> getUnreadEmails();

    MailService mailService = createMailService();

    static MailService createMailService() {
        if (mailService == null) {
            MailService ms = new MockMailService();
            if (!isMailServiceMocked()) {
                SMTPConfiguration config = new SMTPConfiguration(System.getenv("MM_EMAIL_USERID"), System.getenv(EMAIL_PASSWORD), SMTP_ADDR, PORT);
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
}

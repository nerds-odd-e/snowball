package com.odde.snowball.service;

import com.odde.snowball.exception.EmailException;
import com.odde.snowball.model.Mail;

import javax.mail.Session;
import java.util.Properties;

public interface MailService {
    String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";
    String SMTP_ADDR = "smtp.gmail.com";
    int SMTP_PORT = 587;

    void send(Mail email) throws EmailException;

    MailService mailService = createMailService();

    static MailService createMailService() {
        if (mailService != null) {
            return mailService;
        }

        MailService ms = new MockMailService();
        if (!isMailServiceMocked()) {
            MailConfiguration config = new MailConfiguration(System.getenv("MM_EMAIL_USERID"),
                    System.getenv(EMAIL_PASSWORD),
                    ServerConfig.get(SMTP_ADDR, SMTP_PORT)
                    );
            final Session mailSession = createMailSession();
            ms = new GMailService(config, mailSession);
        }
        return ms;

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

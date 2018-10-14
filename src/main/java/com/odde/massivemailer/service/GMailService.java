package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

import javax.mail.*;
import java.util.List;

public class GMailService implements MailService {

    private final MailConfiguration mailConfig;
    public final Session session;

    public GMailService(MailConfiguration config, Session session) {
        mailConfig = config;
        this.session = session;
    }

    @Override
    public void send(Mail email) throws EmailException {
        List<Message> msg;
        try {
            msg = email.createMessages(session);

        } catch (MessagingException ex) {
            throw new EmailException("Unable to send an email: " + ex);
        }
        this.sendEmailViaGmail(msg);
    }

    private void sendEmailViaGmail(List<Message> msgs) throws EmailException {
        try {

            Transport transport = mailConfig.getSmtpTransport(session);

            for (Message msg : msgs) {
                transport.sendMessage(msg, msg.getAllRecipients());
            }
            transport.close();

        } catch (Exception ex) {
            throw new EmailException("Unable to send an email: " + ex);
        }
    }

}

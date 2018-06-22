package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.exception.MailBoxReadException;
import com.odde.massivemailer.model.Mail;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GMailService implements MailService {

    private MailConfiguration mailConfig;
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

    @Override
    public List<Mail> readEmail(boolean readFlag) throws MessagingException {

        Store store = mailConfig.getImapStore(session);

        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);
        final Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        return Arrays.stream(messages)
                .map(this::toMail)
                .collect(Collectors.toList());
    }

    private Mail toMail(Message message) {
        try {
            return new Mail(message.getMessageNumber(), message.getSubject(), message.toString());
        } catch (MessagingException ex) {
            throw new MailBoxReadException("Unable to map email to message", ex);
        }
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

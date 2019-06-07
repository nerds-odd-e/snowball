package com.odde.massivemailer.model;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.service.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Mail {

    private static final String FROM = "myodde@gmail.com";
    private static final String DISPLAY_NAME = "Inspector Gadget";

    private MimeMessage message;
    private List<String> receipts;
    private String subject;
    private String content;
    private long messageId;
    private SentMail sentMail;


    public Mail() {
        receipts = new ArrayList<>();
    }

    public Mail(long messageId, String subject, String content){
        this.messageId = messageId;
        this.subject = subject;
        this.content = content;
    }

    public static Mail  createUpcomingCoursesEmail(String content, String email) {
        Mail mail = new Mail(System.currentTimeMillis(), "Course Invitation", content);
        mail.setReceipts(Collections.singletonList(email));
        return mail;
    }

    public List<String> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<String> receipts) {
        this.receipts = receipts;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private MimeMessage setMessageProperty(Session session, SentMailVisit sentMailVisit)
            throws MessagingException {

        try {
            ContactPerson contact = ContactPerson.getContactByEmail(sentMailVisit.getEmailAddress());
            String subject, content;
            if (contact != null) {
                subject = ReplaceAttibute(this.getSubject(), contact);
                content = ReplaceAttibute(this.getContent(), contact);
            } else {
                subject = getSubject();
                content = getContent();
            }

            message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM, DISPLAY_NAME));
            message.setSubject(subject);

            InetAddress ip = InetAddress.getLocalHost();

            String messageContent = "<html><body>" + content + "<img height=\"42\" width=\"42\" src=\"http://" + ip.getHostAddress() + ":8070/resources/images/qrcode.png?token=" + sentMailVisit.getId() + "\"></img></body></html>";
            message.setText(messageContent);
            message.setContent(messageContent, "text/html; charset=utf-8");

        } catch (UnsupportedEncodingException | UnknownHostException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String ReplaceAttibute(String template, ContactPerson contact) {
        AtomicReference<String> result = new AtomicReference<>(template);
        contact.asAMap().forEach((key, value)->{
            String regexp = "(?i)(^|[^{])(\\{" + key + "})([^}]|$)";
            result.set(result.get().replaceAll(regexp, "$1" + value + "$3"));
        });
        return result.get();
    }

    public List<Message> createMessages(Session session) throws EmailException,
            MessagingException {

        List<Message> returnMsg = new ArrayList<>();

        List<SentMailVisit> sentMailVisits = sentMail.getSentMailVisits();

        for (SentMailVisit sentMailVisit : sentMailVisits) {
            MimeMessage message = setMessageProperty(session, sentMailVisit);
            composeMessage(sentMailVisit.getEmailAddress(), message);
            returnMsg.add(message);

        }

        return returnMsg;
    }

    public void composeMessage(String recipient, MimeMessage message)
            throws EmailException {
        try {

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    recipient));

        } catch (MessagingException ex) {
            throw new EmailException("Unable to send an email: " + ex);
        }
    }

    public SentMail asSentMail() {
        sentMail = new SentMail();
        sentMail.setReceivers(String.join(";", getReceipts()));
        sentMail.setSubject(getSubject());
        sentMail.setContent(getContent());
        sentMail.setMessageId(getMessageId());
        sentMail.saveIt();

        for (String receipt : getReceipts()) {
            sentMail.addEmailAddress(receipt);
        }

        return sentMail;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getMessageId() {
        return messageId;
    }

    public SentMail getSentMail() {
        return sentMail;
    }

    public SentMail sendMailWith(MailService mailService) throws EmailException {
        SentMail sentMail = asSentMail();
        sentMail.saveIt();
        mailService.send(this);
        return sentMail;
    }

    public void sendMailToRecipients(List<String> receipts, MailService mailService) throws EmailException {
		setReceipts(receipts);
		sendMailWith(mailService);
	}

    public void sendMailToRecipient(String emailAddress, MailService mailService) throws EmailException {
		List<String> receipts = new ArrayList<>();
		receipts.add(emailAddress);
		sendMailToRecipients(receipts, mailService);
	}
}

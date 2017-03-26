package com.odde.massivemailer.model;

import com.odde.massivemailer.exception.EmailException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Mail {

    private static final String FROM = "myodde@gmail.com";
    private static final String DISPLAY_NAME = "Inspector Gadget";

    private MimeMessage message;
    private List<String> receipts;
    private String subject;
    private String content;
    private String key;
    private long messageId;
    private Notification notification;


    public Mail() {
        receipts = new ArrayList<>();
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

    private MimeMessage setMessageProperty(Session session, NotificationDetail notificationDetail)
            throws MessagingException {

        try {
            ContactPerson contact = ContactPerson.getContactByEmail(notificationDetail.getEmailAddress());
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

            String messageContent = "<html><body>" + content + "<img height=\"42\" width=\"42\" src=\"http://" + ip.getHostAddress() + ":8070/massive_mailer/resources/images/qrcode.png?token=" + notificationDetail.getId() + "\"></img></body></html>";
            message.setText(messageContent);
            message.setContent(messageContent, "text/html; charset=utf-8");

        } catch (UnsupportedEncodingException | UnknownHostException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String ReplaceAttibute(String template, ContactPerson contact) {

        for (String attr : contact.getAttributeKeys()) {
            String regexp = "(?i)(^|[^\\{])(\\{" + attr + "\\})([^\\}]|$)";

            template = template.replaceAll(regexp, "$1" + contact.getAttribute(attr) + "$3");
        }

        return template;
    }

    public void setMessage(MimeMessage message) {
        this.message = message;
    }

    public List<Message> createMessages(Session session) throws EmailException,
            AddressException, MessagingException {

        List<Message> returnMsg = new ArrayList<Message>();

        List<NotificationDetail> notificationDetails = notification.getNotificationDetails();

        for (NotificationDetail notificationDetail : notificationDetails) {
            MimeMessage message = setMessageProperty(session, notificationDetail);
            composeMessage(notificationDetail.getEmailAddress(), message);
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

    public Notification asNotification() {
        Notification notification = new Notification();
        notification.setSubject(getSubject());
        notification.setNotificationId(getMessageId());

        for (String receipt : getReceipts()) {
            notification.addEmailAddress(receipt);
        }

        return notification;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }
}

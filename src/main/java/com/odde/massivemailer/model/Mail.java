package com.odde.massivemailer.model;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.service.impl.SqliteContact;

public class Mail {

    private static final String FROM = "myodde@gmail.com";
    private static final String DISPLAY_NAME = "Inspector Gadget";

	private MimeMessage message;
	private List<String> receipts;
	private String subject;
	private String content;
	private SqliteContact sqliteContact;
	private String key;
	private Date sentDate;
    private long messageId;
    private Notification notification;




    public Mail() {
        this.sqliteContact = new SqliteContact();
        receipts = new ArrayList<>();
    }

    public Mail(SqliteContact sqliteContact) {
        this.sqliteContact = sqliteContact;
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
            throws AddressException, MessagingException {

        try {
            ContactPerson contact = sqliteContact.getContactByEmail(notificationDetail.getEmailAddress());
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

            String messageContent = "<html><body>" + content + "<img height=\"42\" width=\"42\" src=\"http://"+ip.getHostAddress()+":8070/massive_mailer/resources/images/qrcode.png?token="+notificationDetail.getId()+"\"></img></body></html>";
            message.setText(messageContent);
            message.setContent(messageContent, "text/html; charset=utf-8");

        } catch (UnsupportedEncodingException | SQLException | UnknownHostException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String ReplaceAttibute(String template, ContactPerson contact) {

        for (String attr : contact.getAttributeKeys()) {
            String regexp = "(^|[^\\{])(\\{" + attr + "\\})([^\\}]|$)";

            template = template.replaceAll(regexp, "$1" + contact.getAttribute(attr) + "$3");
        }

        return template;
    }

    public void setMessage(MimeMessage message) {
        this.message = message;
    }

    public List<Message> createMessages(Session session) throws EmailException,
            AddressException, MessagingException {

        List<String> recipients = getReceipts();
        List<Message> returnMsg = new ArrayList<Message>();

        List<NotificationDetail> notificationDetails = notification.getNotificationDetails();

        for(NotificationDetail notificationDetail:notificationDetails){
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

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
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

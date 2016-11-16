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

    public Mail() {
        this.sqliteContact = new SqliteContact();
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
        //this.content ="<html><body>"+content+ "<img src=\"http://192.168.1.90:8070/massive_mailer/resources/images/qrcode.png?messageId="+messageId+"\">Test.gif</img></body></html>";
        this.content = content;
    }

    private MimeMessage setMessageProperty(Session session, String recipient)
            throws AddressException, MessagingException {

        try {
            ContactPerson contact = sqliteContact.getContactByEmail(recipient);
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

            String messageContent = "<html><body>" + content + "<img src=\"http://"+ip.getHostAddress()+":8070/massive_mailer/resources/images/qrcode.png?messageId="+messageId+"&userId="+recipient+"\">Test.gif</img></body></html>";
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

        for (String recipient : recipients) {
            MimeMessage message = setMessageProperty(session, recipient);

            composeMessage(recipient, message);
            long messageId = System.currentTimeMillis();

            returnMsg.add(message);
        }

        return returnMsg;
    }

    // public void composeMessage(List<String> recipients, MimeMessage message)
    // throws EmailException {
    // try {
    //
    // InternetAddress[] toAddress = new InternetAddress[recipients.size()];
    //
    // for (int i = 0; i < recipients.size(); i++) {
    // toAddress[i] = new InternetAddress(recipients.get(i));
    // message.addRecipient(Message.RecipientType.TO, toAddress[i]);
    // }
    //
    // } catch (MessagingException ex) {
    // throw new EmailException("Unable to send an email: " + ex);
    // }
    // }

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
}

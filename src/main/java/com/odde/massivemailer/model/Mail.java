package com.odde.massivemailer.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.odde.massivemailer.exception.EmailException;

public class Mail {

	private static final String FROM = "myodde@gmail.com";
	private static final String DISPLAY_NAME = "Inspector Gadget";

	private MimeMessage message;
	private List<String> receipts;
	private String subject;
	private String content;

	public Mail() {
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

	private MimeMessage setMessageProperty(Session session)
			throws AddressException, MessagingException {
		
		try {
			// TODO: Replace with the value from service
			ContactPerson contact = new ContactPerson("John", "john@gmail.com", "Doe");

			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM, DISPLAY_NAME));
			message.setSubject(ReplaceAttibuteValue(this.getSubject(), contact));
			message.setText(ReplaceAttibuteValue(this.getContent(), contact));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
	}

	public String ReplaceAttibuteValue(String template, ContactPerson contact) {
		for (String attr : ContactPerson.ATTRIBUTES) {
			String regexp = "(^|[^\\{])(\\{" + attr + "\\})([^\\}]|$)";

			template = template.replaceAll(regexp, "$1" + contact.getAttributeValue(attr) + "$3");
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
			MimeMessage message = setMessageProperty(session);
			composeMessage(recipient, message);
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
}

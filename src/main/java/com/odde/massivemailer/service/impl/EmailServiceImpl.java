package com.odde.massivemailer.service.impl;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.MailService;

public class EmailServiceImpl implements MailService {

	private static final String FROM = "myodde@gmail.com";
	private static final String PASSWD = "1234qwer@";
	private static final String HOST = "smtp.gmail.com";

	private static Session session;
	
	public EmailServiceImpl() {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.user", FROM);
		props.put("mail.smtp.password", PASSWD);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		
		session = Session.getDefaultInstance(props);
	}
	
	private Message createMessage(Mail email) throws EmailException {
		
		List<String> recipients = email.getReceipts();
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(FROM));
			message.setSubject(email.getSubject());
			message.setText(email.getContent());
			
			InternetAddress[] toAddress = new InternetAddress[recipients.size()];

			// To get the array of addresses
			for (int i = 0; i < recipients.size(); i++) {
				toAddress[i] = new InternetAddress(recipients.get(i));				
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			
		} catch (MessagingException ex) {
			throw new EmailException("Unable to send an email: " + ex);
		}
		
		return message;
	}

	@Override
	public void send(Mail email) throws EmailException {
		Message msg = this.createMessage(email);
		try {
			
			Transport transport = session.getTransport("smtp");
			transport.connect(HOST, FROM, PASSWD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			
		} catch (Exception ex) {
			throw new EmailException("Unable to send an email: " + ex);
		}

	}

}

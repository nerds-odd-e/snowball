package com.odde.massivemailer.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.MailService;

public class GMailService implements MailService {

	private static final String FROM = "myodde@gmail.com";
	private static final String PASSWD = "1234qwer@";
	private static final String HOST = "smtp.gmail.com";

	public static Session session;

	public GMailService() {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.user", FROM);
		props.put("mail.smtp.password", PASSWD);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		session = Session.getDefaultInstance(props);
	}

	@Override
	public void send(Mail email) throws EmailException {
		Message msg;
		try {
			msg = email.createMessage(session);
		} catch (MessagingException ex) {
			throw new EmailException("Unable to send an email: " + ex);
		}
		this.sendEmailViaGmail(msg);
	}

	private void sendEmailViaGmail(Message msg) throws EmailException {
		try {

			Transport transport = getTransport();
			transport.connect(HOST, FROM, PASSWD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (Exception ex) {
			throw new EmailException("Unable to send an email: " + ex);
		}
	}

	protected Transport getTransport() throws NoSuchProviderException {
		return session.getTransport("smtp");
	}
	
}

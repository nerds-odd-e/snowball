package com.odde.massivemailer.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.SMTPConfiguration;

public class GMailService implements MailService {

	private SMTPConfiguration configuration;

	public static Session session;

	public GMailService(SMTPConfiguration config) {

        configuration = config;

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		session = Session.getDefaultInstance(props);
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

	public void setConfiguration(SMTPConfiguration config) {
		this.configuration = config;
	}

	private void sendEmailViaGmail(List<Message> msgs) throws EmailException {
		try {

			Transport transport = getTransport();
			transport.connect(configuration.HOST, configuration.PORT, configuration.FROM, configuration.PASSWD);
			for (Message msg : msgs) {
				transport.sendMessage(msg, msg.getAllRecipients());
			}

			transport.close();

		} catch (Exception ex) {
			throw new EmailException("Unable to send an email: " + ex);
		}
	}

	protected Transport getTransport() throws NoSuchProviderException {
		return session.getTransport("smtp");
	}

//	public long getDistance(double srcLat, double srcLong, double destLat, double destLong) {
//
//
//	}

}

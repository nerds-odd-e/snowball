package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;

//TODO remove comments
public class GMailService implements MailService {

	private SMTPConfiguration configuration;

	public final Session session;

	public GMailService(SMTPConfiguration config, Session session) {
        configuration = config;
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
	public List<Mail> readEmail(boolean readFlag) {

		return new ArrayList<>();
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
}

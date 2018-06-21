package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.exception.MailBoxReadException;
import com.odde.massivemailer.model.Mail;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	public Set<Mail> getUnreadEmails() {
		try {
			Store store = session.getStore("imaps");
			store.connect(configuration.HOST, configuration.PORT, configuration.FROM, configuration.PASSWD);
			Folder inbox = store.getFolder("inbox");
			inbox.open(Folder.READ_ONLY);

			final Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			return Arrays.stream(messages)
					.map(this::toMail)
					.collect(Collectors.toSet());

		} catch (MessagingException ex) {
			throw new MailBoxReadException("Unable to read messages", ex);
		}
	}

	private Mail toMail(Message message) {
		try {
			return new Mail(message.getMessageNumber(), message.getSubject(), getTextFromMessage(message));
		} catch (MessagingException | IOException ex) {
			throw new MailBoxReadException("Unable to map email to message", ex);
		}
	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(
			MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
//				result = result + "\n" + Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
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

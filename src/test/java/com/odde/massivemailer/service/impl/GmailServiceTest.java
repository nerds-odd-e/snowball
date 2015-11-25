package com.odde.massivemailer.service.impl;

import java.util.Arrays;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Transport;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Test;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.MailService;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GmailServiceTest {

	private static String[] RECIPIENTS = new String[] { "myodde@gmail.com",
			"kit.sumate@gmail.com" };

	private Mail createEmail() {
		Mail email = new Mail();
		email.setContent("Hi {FirstName}");
		email.setSubject("test subject");
		email.setReceipts(Arrays.asList(RECIPIENTS));
		return email;
	}
	
	private MailService getEmailService(final Transport transport) {
		SMTPConfiguration config = new SMTPConfiguration("myodde@gmail.com", "1234qwer@", "smtp.gmail.com", 587);
		MailService emailService = new GMailService() {
			@Override
			protected Transport getTransport() throws NoSuchProviderException {
				return transport;
			} 
		};

		emailService.setConfiguration(config);

		return emailService;
	}
    
	@Test
	public void testSend() throws EmailException, MessagingException {
		final Transport transport = mock(Transport.class);
		MailService emailService = this.getEmailService(transport);
		Mail email = createEmail();		
		emailService.send(email);
		verify(transport, times(1)).connect(anyString(), anyInt(), anyString(), anyString());
		verify(transport, times(1)).sendMessage(any(Message.class), any(Address[].class));
		verify(transport, times(1)).close();

	}
	
	@Test
	public void testSend_multipleRecipients() throws EmailException, MessagingException {
		final Transport transport = mock(Transport.class);
		MailService emailService = this.getEmailService(transport);
		Mail email = createEmail();
		email.setReceipts(Arrays.asList(RECIPIENTS));
		emailService.send(email);
		verify(transport, times(1)).connect(anyString(), anyInt(), anyString(), anyString());
		verify(transport, times(2)).sendMessage(any(Message.class), any(Address[].class));
		verify(transport, times(1)).close();

	}

	@Test(expected = EmailException.class)
	public void testSend_failed() throws Exception {
		Transport transport = null;
		MailService emailService = this.getEmailService(transport);
		Mail email = createEmail();
		emailService.send(email);
	}

	@Test
	public void sendEmailViaGreenMailSMTP() throws EmailException {
		//Arrange
		GreenMail greenMail = new GreenMail(new ServerSetup(3025, null, "smtp"));
		greenMail.start();
		SMTPConfiguration config = new SMTPConfiguration("fake@greenmail.com", "*******", "localhost", 3025);

		MailService mailService = new GMailService();

		mailService.setConfiguration(config);

		//Act
		Mail email = createEmail();
		mailService.send(email);

		//Assert
		assertEquals("Hi John", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
		assertEquals("Hi John", GreenMailUtil.getBody(greenMail.getReceivedMessages()[1]));
		greenMail.stop();
	}

}

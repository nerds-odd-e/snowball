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
import org.junit.Ignore;
import org.junit.Test;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GmailServiceTest {

	private static String[] RECIPIENTS = new String[] { "myodde@gmail.com",
			"kit.sumate@gmail.com" };

	private Mail createEmail() {
		Mail email = new Mail();
		email.setContent("Hi Dude");
		email.setSubject("test subject");
		email.setReceipts(Arrays.asList(RECIPIENTS));
		return email;
	}
	
	private GMailService getGmailService(final Transport transport) {
		SMTPConfiguration config = new SMTPConfiguration("fakeUser@gmail.com", "fakeUserPassword", "smtp.gmail.com", 587);
		GMailService gmailService = new GMailService(config) {
			@Override
			protected Transport getTransport() throws NoSuchProviderException {
				return transport;
			} 
		};

		return gmailService;
	}
   
	
	@Test
	public void testSend_multipleRecipients() throws EmailException, MessagingException {
		final Transport transport = mock(Transport.class);
        GMailService emailService = this.getGmailService(transport);
		Mail email = createEmail();
		email.setReceipts(Arrays.asList(RECIPIENTS));
		emailService.send(email);
		verify(transport, times(1)).connect(anyString(), anyInt(), anyString(), anyString());
		verify(transport, times(RECIPIENTS.length)).sendMessage(any(Message.class), any(Address[].class));
		verify(transport, times(1)).close();

	}

	@Test(expected = EmailException.class)
	public void testSend_failed() throws Exception {
		Transport transport = null;
        GMailService emailService = this.getGmailService(transport);
		Mail email = createEmail();
		emailService.send(email);
	}

	@Test
	public void sendEmailViaGreenMailSMTP() throws EmailException {
		//Arrange
		GreenMail greenMail = new GreenMail(new ServerSetup(3025, null, "smtp"));
		greenMail.start();
		SMTPConfiguration config = new SMTPConfiguration("fake@greenmail.com", "*******", "localhost", 3025);

        GMailService mailService = new GMailService(config);


        //Act
		Mail email = createEmail();
		mailService.send(email);

		//Assert
		assertEquals("<html><body>Hi Dude</body></html>", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
		assertEquals("<html><body>Hi Dude</body></html>", GreenMailUtil.getBody(greenMail.getReceivedMessages()[1]));
		greenMail.stop();
	}

}

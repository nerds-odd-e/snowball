package com.odde.massivemailer.service.impl;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.MailService;

public class EmailServiceImplTest {

	private static String[] RECIPIENT = new String[] { "myodde@gmail.com",
			"kit.sumate@gmail.com" };

	private final Mail email;
	private final MailService emailService;

	public EmailServiceImplTest() {
		emailService = new EmailServiceImpl();
		email = new Mail();
		email.setContent("test conent");
		email.setSubject("test subject");
	}

	@Test
	public void testSend() {
		email.setReceipts(Arrays.asList(RECIPIENT[0]));
		try {
			emailService.send(email);
		} catch (EmailException ex) {
			fail("Unable to send an email: " + ex);
		}

	}

	@Test
	public void testSend_multipleRecipients() {
		email.setReceipts(Arrays.asList(RECIPIENT));
		try {
			emailService.send(email);
		} catch (EmailException ex) {
			fail("Unable to send an email: " + ex);
		}

	}

	@Test(expected = EmailException.class)
	public void testSend_failed() throws Exception {
		email.setReceipts(Collections.<String> emptyList());
		emailService.send(email);
	}
}

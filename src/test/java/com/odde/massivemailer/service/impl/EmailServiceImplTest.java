package com.odde.massivemailer.service.impl;

import org.junit.Test;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.MailService;

public class EmailServiceImplTest {
	
	@Test
	public void testSend() {
		MailService emailService = new EmailServiceImpl();
		
	}
	
	
	@Test(expected = EmailException.class)
	public void testSend_failed() {
//		Mail mail = new Mail();
//		email.
//		MailService emailService = new EmailServiceImpl();
//		emailService.send(email);
		
	}
}

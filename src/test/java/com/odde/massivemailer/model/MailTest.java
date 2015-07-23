package com.odde.massivemailer.model;

import java.util.Arrays;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

import javax.mail.Message;
import javax.mail.Session;

import org.junit.Test;

public class MailTest {
	
	@Test
	public void testCreateMessage() throws Exception {

		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		Mail mail = new Mail();
		mail.setContent("content");
		mail.setSubject("subject");
		mail.setReceipts(Arrays.asList("test@gmail.com"));
		
		Message message = mail.createMessage(session);
		
		assertEquals(mail.getSubject(), message.getSubject());
	}
	
}

package com.odde.massivemailer.model;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

import javax.mail.Address;
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
		
		List<Message> messages = mail.createMessages(session);
		
		assertEquals(1, messages.size());
		assertEquals(mail.getSubject(), messages.get(0).getSubject());
	}
	
	@Test
	public void testDisplayName() throws Exception {

		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		
		Mail mail = new Mail();
		mail.setContent("content");
		mail.setSubject("subject");
		mail.setReceipts(Arrays.asList("test@gmail.com"));
		
		List<Message> messages = mail.createMessages(session);
		String[] address = messages.get(0).getFrom()[0].toString().split("<");
		
		assertEquals("Inspector Gadget", address[0].trim());
	}

	@org.junit.Test
	public void testReplaceAttributeValue(){
		String FirstNameTemplate = "Greeting {FirstName}";
		String LastNameTemplate = "Greeting {LastName}";
		String CompanyTemplate = "Greeting {Company}";
		ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe");

		Mail mail = new Mail();

		String fResult = mail.ReplaceAttibuteValue(FirstNameTemplate, contact);
		String lResult = mail.ReplaceAttibuteValue(LastNameTemplate, contact);
		//String cResult = mail.ReplaceAttibuteValue(CompanyTemplate, contact);

		assertEquals("Greeting John", fResult);
		assertEquals("Greeting Doe", lResult);
		//assertEquals("Greeting TR", cResult);
	}

	@org.junit.Test
	public void testEscapeSyntax(){
		String FirstNameTemplate = "Greeting {{{FirstName}}} {{FirstName}} {FirstName} FirstName";
		String LastNameTemplate = "Greeting {{{LastName}}} {{LastName}} {LastName} LastName";
		String CompanyTemplate = "Greeting {{Company}";
		ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe");

		Mail mail = new Mail();

		String fResult = mail.ReplaceAttibuteValue(FirstNameTemplate, contact);
		String lResult = mail.ReplaceAttibuteValue(LastNameTemplate, contact);
		//String cResult = mail.ReplaceAttibuteValue(CompanyTemplate, contact);

		assertEquals("Greeting {{{FirstName}}} {{FirstName}} John FirstName", fResult);
		assertEquals("Greeting {{{LastName}}} {{LastName}} Doe LastName", lResult);
		//assertEquals("Greeting TR", cResult);
	}

}

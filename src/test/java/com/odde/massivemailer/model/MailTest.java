package com.odde.massivemailer.model;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

import javax.mail.Message;
import javax.mail.Session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

public class MailTest {
	Properties props = System.getProperties();
	Session session = Session.getDefaultInstance(props);
	Mail mailWithTwoRecipients;

	@Before
	public void setUp() {
		mailWithTwoRecipients = createMailWithTwoRecipients();
	}

	@Test
	public void multipleRecipients() throws Exception {
		List<Message> messages = mailWithTwoRecipients.createMessages(session);

		assertEquals(2, messages.size());
		assertEquals(mailWithTwoRecipients.getRecipients().get(0), messages.get(0).getRecipients(Message.RecipientType.TO)[0].toString());
		assertEquals(mailWithTwoRecipients.getRecipients().get(1), messages.get(1).getRecipients(Message.RecipientType.TO)[0].toString());
	}

	@Test
	public void messageIsSet() throws Exception {
		List<Message> messages = mailWithTwoRecipients.createMessages(session);

		assertEquals(mailWithTwoRecipients.getSubject(), messages.get(0).getSubject());
		assertEquals(mailWithTwoRecipients.getContent(), messages.get(0).getContent());
	}

	@Test
	public void testDisplayName() throws Exception {
		List<Message> messages = mailWithTwoRecipients.createMessages(session);
		String[] address = messages.get(0).getFrom()[0].toString().split("<");

		assertEquals("Inspector Gadget", address[0].trim());
	}

	private Mail createMailWithTwoRecipients() {
		Mail mail = new Mail();
		mail.setContent("valid content");
		mail.setSubject("valid subject");
		mail.setRecipients(Arrays.asList("test1@gmail.com","test2@gmail.com"));
		return mail;
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
		//String cResult = validMail.ReplaceAttibuteValue(CompanyTemplate, contact);

		assertEquals("Greeting {{{FirstName}}} {{FirstName}} John FirstName", fResult);
		assertEquals("Greeting {{{LastName}}} {{LastName}} Doe LastName", lResult);
		//assertEquals("Greeting TR", cResult);
	}
}

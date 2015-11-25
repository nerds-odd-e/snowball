package com.odde.massivemailer.model;

import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ContactPersonTest {

	@Test
	public void testGetAttributeValue() throws Exception {
		ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe");

		String firstName = contact.getAttributeValue(ContactPerson.FIRSTNAME);
		String lastName = contact.getAttributeValue(ContactPerson.LASTNAME);
		String email = contact.getAttributeValue(ContactPerson.EMAIL);
		String invalidKey = contact.getAttributeValue("Invalid");

		assertEquals("John", firstName);
		assertEquals("Doe", lastName);
		assertEquals("john@gmail.com", email);
		assertEquals("", invalidKey);
	}

}

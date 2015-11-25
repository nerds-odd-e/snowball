package com.odde.massivemailer.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ContactPersonTest {
	
	@Test
	public void testCreateContactObjectWithoutCompany() {
		
		String name = "name";
		String email = "email@abc.com";
		String lastname = "lastname";
		ContactPerson person = new ContactPerson(name, email, lastname);
		
		assertEquals(name, person.getName());
		assertEquals(email, person.getEmail());
		assertEquals(lastname, person.getLastname());
		assertEquals("", person.getCompany());
	}
	
	
	@Test
	public void testCreateContactObjectWithCompany() {
		
		String name = "name";
		String email = "email@abc.com";
		String lastname = "lastname";
		String company = "myCompany";
		ContactPerson person = new ContactPerson(name, email, lastname, company);
		
		assertEquals(name, person.getName());
		assertEquals(email, person.getEmail());
		assertEquals(lastname, person.getLastname());
		assertEquals(company, person.getCompany());
	}

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

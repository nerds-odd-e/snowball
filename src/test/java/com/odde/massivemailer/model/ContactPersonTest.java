package com.odde.massivemailer.model;

import static org.junit.Assert.*;

import org.junit.Test;

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
	public void testGetInvalidAttributeValue() throws Exception {
		ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe");

		String invalidKey = contact.getAttribute("Invalid");

		assertEquals("", invalidKey);
	}



}

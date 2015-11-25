package com.odde.massivemailer.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContactPersonTest {
	
	@Test
	public void sameContactPerson() throws Exception {
		ContactPerson john = new ContactPerson("John", "john@gmail.com", "Doe", "Apple");
		ContactPerson sameJohn = new ContactPerson("John", "john@gmail.com", "Doe", "Apple");
		
		assertEquals(john, sameJohn);
	}
	
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

<<<<<<< d9cc6196f56a9405d8f2923f802f99b51f536b54
	@Test
	public void testGetInvalidAttributeValue() throws Exception {
		ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe");

		String invalidKey = contact.getAttribute("Invalid");

		assertEquals("", invalidKey);
	}


=======
	
>>>>>>> Add getContactList by company
}

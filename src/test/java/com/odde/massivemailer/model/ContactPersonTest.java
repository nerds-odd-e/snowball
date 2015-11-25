package com.odde.massivemailer.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactPersonTest {

	@Test
	public void testGetInvalidAttributeValue() throws Exception {
		ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe");

		String invalidKey = contact.getAttribute("Invalid");

		assertEquals("", invalidKey);
	}


}

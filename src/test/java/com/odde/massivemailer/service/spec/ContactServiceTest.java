package com.odde.massivemailer.service.spec;

import static org.junit.Assert.*;

import org.junit.Test;

import com.odde.massivemailer.service.ContactService;

public class ContactServiceTest {

	ContactService service = new ContactService();
	
	@Test
	public void CheckPreCondition() {
		assertEquals("localhost", service.localhost);
		assertEquals("root", service.username);
		assertEquals("admin", service.password);
		assertEquals("jdbc:mysql://localhost/oddemail", service.URL);
	}
	
	@Test
	public void CheckRegisterStep() {
		assertEquals("com.mysql.jdbc.Driver", service.driverName);
		
	}

	@Test
	public void CheckSqlSelect() {
		assertEquals("SELECT id, name FROM mail", service.selectMail);
		
	}
}
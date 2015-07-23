package com.odde.massivemailer;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShowContactControllerTest {

	@Test
	public void shouldReturnNameOfListWhenCalledController() {
		ShowContactController showContactController = new ShowContactController();
		assertEquals("mail@hotmail.com",showContactController.getContactData().get(0).getName());
	}
}

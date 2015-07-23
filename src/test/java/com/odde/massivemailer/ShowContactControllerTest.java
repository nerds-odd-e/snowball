package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.odde.massivemailer.controller.ShowContactController;
import com.odde.massivemailer.model.ContactPerson;

public class ShowContactControllerTest {
	ShowContactController showContactController = new ShowContactController();

	@Test
	public void shouldReturnNameOfListWhenCalledController() {
		assertEquals("mail@hotmail.com", showContactController.getContactData().get(0).getName());
	}
	
	@Test
	public void shouldReturnJSONDataOfContact() {
		List<ContactPerson> cpList = new ArrayList<ContactPerson>();
		ContactPerson cp = new ContactPerson();
		cp.setId(1);
		cp.setName("mail@hotmail.com");
		cpList.add(cp);
		String contactJSON = showContactController.convertContactToJSON(cpList);
		System.out.println(contactJSON);
		assertEquals("[{\"id\":1,\"name\":\"mail@hotmail.com\"}]", contactJSON);
	}
}

package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odde.massivemailer.controller.SendMailController;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.impl.SqliteContact;


public class SendMailControllerTest {

	HttpServletRequest httpReq = Mockito.mock(HttpServletRequest.class);
	SqliteContact mockedContact = Mockito.mock(SqliteContact.class);
	SendMailController mailController = new SendMailController();
	
	@org.junit.Test
	public void testProcessRequest() throws SQLException{
		String recipient = "name1@gmail.com;name2@gmail.com";

		Mockito.when(httpReq.getParameter("recipient")).thenReturn(recipient);
		Mockito.when(httpReq.getParameter("content")).thenReturn("content-na-ka");
		Mockito.when(httpReq.getParameter("subject")).thenReturn("suject for test");

		Mail mail = new Mail();
		mail = mailController.processRequest(httpReq);

		Assert.assertEquals("suject for test",mail.getSubject());
		List<String> repList = mail.getReceipts();
		Assert.assertEquals("name1@gmail.com",repList.get(0));
		Assert.assertEquals("name2@gmail.com",repList.get(1));
		Assert.assertEquals("content-na-ka",mail.getContent());
	}
	

	@org.junit.Test
	public void testProcessRequestByCompany() throws SQLException{
		
		String recipient = "name1@gmail.com;company:abc";

		Mockito.when(httpReq.getParameter("recipient")).thenReturn(recipient);
		Mockito.when(httpReq.getParameter("content")).thenReturn("Company-na-ka");
		Mockito.when(httpReq.getParameter("subject")).thenReturn("suject for test");
		
		List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
		contactPersonList.add(new ContactPerson("", "ab1@abc.com", "", "abc"));
		contactPersonList.add(new ContactPerson("", "ab2@abc.com", "", "abc"));
		contactPersonList.add(new ContactPerson("", "ab3@abc.com", "", "abc"));
		Mockito.when(mockedContact.getContactListFromCompany("abc")).thenReturn(contactPersonList);
		
		mailController.setSqliteContact(mockedContact);
		
		Mail mail = new Mail();
		mail = mailController.processRequest(httpReq);

		Assert.assertEquals("suject for test",mail.getSubject());
		Assert.assertEquals("Company-na-ka",mail.getContent());
		List<String> repList = mail.getReceipts();
		Assert.assertEquals("name1@gmail.com",repList.get(0));		
		Assert.assertEquals("ab1@abc.com",repList.get(1));
		Assert.assertEquals("ab2@abc.com",repList.get(2));
		Assert.assertEquals("ab3@abc.com",repList.get(3));		
	}
	
}

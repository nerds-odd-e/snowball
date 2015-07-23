package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odde.massivemailer.controller.SendMailController;
import com.odde.massivemailer.model.Mail;


public class SendMailControllerTest {
	
	
	
	HttpServletRequest httpReq = Mockito.mock(HttpServletRequest.class);
	SendMailController mailController = new SendMailController();
	
	@org.junit.Test
	public void testProcessRequest(){
		String recipient = "name1@gmail.com;name2@gmail.com";
		
		Mockito.when(httpReq.getParameter("recipient")).thenReturn(recipient);
		Mockito.when(httpReq.getParameter("content")).thenReturn("content-na-ka");
		Mockito.when(httpReq.getParameter("subject")).thenReturn("suject for test");
		
		Mail mail = mailController.processRequest(httpReq);
		
	    Assert.assertEquals("suject for test",mail.getSubject());
	    List<String> repList = mail.getReceipts();
	    Assert.assertEquals("name1@gmail.com",repList.get(0));
	    Assert.assertEquals("name2@gmail.com",repList.get(1));
	    Assert.assertEquals("content-na-ka",mail.getContent());
	}
	
	@Test
	public void shouldReturnNameOfListWhenCalledController() {
		assertEquals("mail@hotmail.com",mailController.getContactData().get(0).getName());
	}
	
	
	
}

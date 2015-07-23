package com.odde.massivemailer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.impl.EmailServiceImpl;

public class SendMailController extends HttpServlet {

 	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 		 
 		Mail email = processRequest(req);
		MailService mailService =  new EmailServiceImpl();
 		try {
 			mailService.send(email);
			resp.sendRedirect("sendemail.jsp?status=success&msg=Email successfully sent");
 		} catch (EmailException e) {
			// TODO: handling for email sending failure
			resp.sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");
 			e.printStackTrace();
 		}
 	}

	public Mail processRequest(HttpServletRequest req) {

		Mail email = new Mail();
		String tempRecipient = req.getParameter("recipient");
		StringTokenizer st = new StringTokenizer(tempRecipient, ";");
		ArrayList<String> recipientList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			recipientList.add(st.nextToken());
		}

		email.setContent(req.getParameter("content"));
		email.setSubject(req.getParameter("subject"));
		email.setReceipts(recipientList);

		return email;
	}
	
	public List<ContactPerson> getContactData() {
		List<ContactPerson> cpList = new ArrayList<ContactPerson>();
		ContactPerson cp = new ContactPerson();
		cp.setId(1);
		cp.setName("mail@hotmail.com");
		cpList.add(cp);
		return cpList;
	}

}

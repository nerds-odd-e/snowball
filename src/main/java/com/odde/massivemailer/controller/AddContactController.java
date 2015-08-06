package com.odde.massivemailer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;

public class AddContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactService contactService;

	public ContactService getContactService() {
		if (this.contactService == null) {
			this.contactService = new SqliteContact();
		}
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resultMsg =	getContactService().addContact("dummy", req.getParameter("email"));
		resp.sendRedirect("contactlist.jsp?"+resultMsg);
	}
}

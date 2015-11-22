package com.odde.massivemailer.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.impl.SqliteContact;

public class UpdateContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactPerson contactPerson = new ContactPerson();
		contactPerson.setName(req.getParameter("name"));
		contactPerson.setEmail(req.getParameter("email"));
		contactPerson.setLastname(req.getParameter("lastname"));
		SqliteContact service = new SqliteContact();
		try {
			service.updateContact(contactPerson);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("contactlist.jsp");
	}
}

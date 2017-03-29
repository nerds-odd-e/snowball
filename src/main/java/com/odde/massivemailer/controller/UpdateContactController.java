package com.odde.massivemailer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.model.ContactPerson;

@WebServlet("/editContact")
public class UpdateContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactPerson contactPerson = ContactPerson.getContactByEmail(req.getParameter("email"));
		if (contactPerson != null) {
			contactPerson.setName(req.getParameter("name"));
			contactPerson.setLastname(req.getParameter("lastname"));
			contactPerson.setCompany(req.getParameter("company"));
			contactPerson.setLocation(req.getParameter("location"));
			contactPerson.saveIt();
			resp.sendRedirect("contactlist.jsp");

		}

	}
}

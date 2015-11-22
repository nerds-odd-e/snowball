package com.odde.massivemailer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;

public class AddContactController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContactService contactService;

    public AddContactController() {
        contactService = new SqliteContact();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg = "";

        ContactPerson contact = new ContactPerson("todo name", req.getParameter("email"), "todo last name");
        if (contactService.addContact(contact))
            resultMsg = "status=success&msg=Add contact successfully";
        else
            resultMsg = "status=failed&msg=Email " + req.getParameter("email")
                    + " is already exist";

        resp.sendRedirect("contactlist.jsp?" + resultMsg);
    }
}

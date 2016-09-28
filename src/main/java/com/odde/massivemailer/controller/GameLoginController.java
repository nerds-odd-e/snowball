package com.odde.massivemailer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.exception.InvalidEmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;

public class GameLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContactService contactService;

    public GameLoginController() {
        contactService = new SqliteContact();
    }

    public GameLoginController(ContactService contactService) {
        this.contactService = contactService;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        ContactPerson contact;
        try {
            contact = new ContactPerson("todo name", email, "todo last name", "todo company");
            contactService.addContact(contact);
            req.getSession().setAttribute("email", email);
            resp.sendRedirect("EmersonsGame");
        } catch(InvalidEmailException ex) {
            resp.sendRedirect("game_login.jsp?error=Invalid+email+provided!");
        }

    }
}

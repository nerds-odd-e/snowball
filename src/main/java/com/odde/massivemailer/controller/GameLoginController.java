package com.odde.massivemailer.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;
import com.odde.massivemailer.utilities.EmailValidator;

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

        if(EmailValidator.isEmailValid(email)) {
            ContactPerson contact = new ContactPerson("todo name", email, "todo last name", "todo company");
            contactService.addContact(contact);
            resp.sendRedirect("game_player.jsp");
        } else {

            resp.sendRedirect("game_login.jsp?error=Invalid+email+provided!");
        }


    }
}

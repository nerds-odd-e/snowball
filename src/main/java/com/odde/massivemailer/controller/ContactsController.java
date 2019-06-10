package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.model.base.ValidationException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.odde.massivemailer.model.base.Repository.repo;

@WebServlet("/contacts")
public class ContactsController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        if (email == null) {
            respondWithJSON(resp, repo(ContactPerson.class).findAll());
            return;
        }
        respondWithJSON(resp, ContactPerson.getContactByEmail(email));
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map map = getParameterFromRequest(req, "city", "country", "email", "firstName", "lastName", "company");
        ContactPerson contact = repo(ContactPerson.class).fromMap(map);
        try {
        contact.save();
            User.createUnconfirmedUser(contact.getEmail(), getMailService());
            respondWithRedirectAndSuccessMessage(resp, "contactlist.jsp", "Add contact successfully");
        }
        catch(ValidationException e) {
            respondWithRedirectAndError(resp, "add_contact.jsp", e.errors());
        }
    }

}

package com.odde.massivemailer.controller;

import com.odde.massivemailer.controller.config.ApplicationConfiguration;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contacts")
public class ContactsController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        if (email == null) {
            respondWithJSON(resp, ContactPerson.findAll());
            return;
        }
        respondWithJSON(resp, ContactPerson.getContactByEmail(email));
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String emailAddress = req.getParameter("email");
        if (!ContactPerson.isValidEmail(emailAddress)) {
            respondWithRedirectAndErrorMessage(resp, "add_contact.jsp", "email is invalid");
            return;
        }
        try {
            ContactPerson.createContact(req.getParameter("city"), req.getParameter("country"), emailAddress, req.getParameter("name"), req.getParameter("lastname"), req.getParameter("company"));
        } catch (Exception e) {
            respondWithRedirectAndErrorMessage(resp, "contactlist.jsp", e.getMessage());
            return;
        }
        User user = new User(emailAddress);
        if (user.saveIt()) {
            Mail email = new Mail();
            email.setSubject("");
            email.setContent(new ApplicationConfiguration().getRoot() + "initialPassword?token=" + user.getToken());
            email.sendMailToRecipient(emailAddress, getMailService());
        }
        respondWithRedirectAndSuccessMessage(resp, "contactlist.jsp", "Add contact successfully");
    }
}

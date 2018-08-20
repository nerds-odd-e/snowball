package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.serialiser.AppGson;
import com.odde.massivemailer.service.MailService;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
        if(!ContactPerson.isValidEmail(emailAddress)) {
			resp.sendRedirect("add_contact.jsp?" + "status=fail&msg=error message");
			return;
        }
		try {
			MailService mailService = getMailService();
			ContactPerson.createContact(req.getParameter("city"), req.getParameter("country"), emailAddress, req.getParameter("name"), req.getParameter("lastname"), req.getParameter("company"));
			User user = new User(emailAddress);
			if (user.saveIt()) {
				Mail email = new Mail();
				email.setSubject("");
				String env = System.getProperty("active_env");
				if ("test".equals(env)) {
					email.setContent("http://localhost:8060/massive_mailer/initialPassword?token=" + user.getToken());
				} else {
					email.setContent("http://localhost:8070/massive_mailer/initialPassword?token=" + user.getToken());
				}
				email.sendMailToRecipient(emailAddress, mailService);
			}
			resp.sendRedirect("contactlist.jsp?" + "status=success&msg=Add contact successfully");
		} catch (Exception e) {
			resp.sendRedirect("contactlist.jsp?" + "status=failed&msg=" + e.getMessage());
		}
    }

}

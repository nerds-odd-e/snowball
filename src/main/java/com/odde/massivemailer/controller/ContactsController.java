package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.serialiser.AppGson;
import com.odde.massivemailer.service.MailService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contacts")
public class ContactsController extends AppController {
    private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String convertedContactToJSON = null;
        if (req.getParameter("email") == null) {
            convertedContactToJSON = AppGson.getGson().toJson(ContactPerson.findAll());
        } else {
            convertedContactToJSON = AppGson.getGson().toJson(ContactPerson.getContactByEmail(req.getParameter("email")));
        }
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertedContactToJSON);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg;

		String emailAddress = req.getParameter("email");
        if(!ContactPerson.isValidEmail(emailAddress)) {
        	resultMsg = "status=fail&msg=error message";
			resp.sendRedirect("add_contact.jsp?" + resultMsg);
			return;
        }
		try {
			MailService mailService = getMailService();
			ContactPerson.createContact(req.getParameter("city"), req.getParameter("country"), emailAddress, req.getParameter("name"), req.getParameter("lastname"), req.getParameter("company"));
			resultMsg = "status=success&msg=Add contact successfully";
			User user = new User(emailAddress);
			if (user.saveIt()) {
				Mail email = new Mail();
				email.setSubject("");
				email.setContent("http://localhost:8060/massive_mailer/initialPassword?token=" + user.getToken());
				email.sendMailToRecipient(emailAddress, mailService);
			}
		} catch (Exception e) {
            resultMsg = "status=failed&msg=" + e.getMessage();
		}
		resp.sendRedirect("contactlist.jsp?" + resultMsg);
    }

}

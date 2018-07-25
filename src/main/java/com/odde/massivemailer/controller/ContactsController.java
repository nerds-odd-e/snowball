package com.odde.massivemailer.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.serialiser.AppGson;

@WebServlet("/contacts")
public class ContactsController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg;
        try {
            ContactPerson.createContact(req.getParameter("city"), req.getParameter("country"), req.getParameter("email"), req.getParameter("name"),req.getParameter("lastname"),req.getParameter("company"));
            resultMsg = "status=success&msg=Add contact successfully";
            if(req.getParameter("consent_id") != null) {
                // TODO generate consent_id
                // TODO send email with the consent_id
                resultMsg += " with existing consent_id";
            }
        } catch (Exception e) {
            resultMsg = "status=failed&msg=" + e.getMessage();
        }

        resp.sendRedirect("contactlist.jsp?" + resultMsg);
    }

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
}

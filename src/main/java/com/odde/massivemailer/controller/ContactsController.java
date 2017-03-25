package com.odde.massivemailer.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;
import org.javalite.activejdbc.Base;

public class ContactsController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg = "";

        ContactPerson contact = new ContactPerson("todo name", req.getParameter("email"), "todo last name", "todo company");
        try {
            contact.validate();
                if (contact.saveIt())
                    resultMsg = "status=success&msg=Add contact successfully";
            else {
                resultMsg = "status=failed&msg1=" + contact.errors();
            }
        } catch (Exception e) {
                resultMsg = "status=failed&msg=" + e.getMessage();
        }

        resp.sendRedirect("contactlist.jsp?" + resultMsg);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String convertedContactToJSON = getGson().toJson(ContactPerson.findAll());
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertedContactToJSON);
    }
}

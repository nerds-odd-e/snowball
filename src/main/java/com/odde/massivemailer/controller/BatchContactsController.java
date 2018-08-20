package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/batchContacts")
public class BatchContactsController extends AppController {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data = req.getParameter("data");
        ContactPerson.createContactsFromCSVData(data);
        resp.sendRedirect("add_contact_batch.jsp");
    }
}
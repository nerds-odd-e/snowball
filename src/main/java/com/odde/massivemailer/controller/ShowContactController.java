package com.odde.massivemailer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;

public class ShowContactController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ContactService contactService;

    public ContactService getContactService() {
        if (this.contactService == null) {
            this.contactService = new SqliteContact();
        }
        return contactService;
    }

    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getContactService();
        String convertedContactToJSON = convertContactToJSON(getContactData());
        writeContactList(resp, convertedContactToJSON);
    }

    private void writeContactList(HttpServletResponse resp, String convertedContactToJSON) throws IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertedContactToJSON);
    }

    public List<ContactPerson> getContactData() {
        return contactService.getContactList();
    }

    public String convertContactToJSON(List<ContactPerson> contactList) {
        return new Gson().toJson(contactList);
    }
}

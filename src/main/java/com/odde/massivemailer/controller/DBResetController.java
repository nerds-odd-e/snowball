package com.odde.massivemailer.controller;

import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DBResetController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContactService contactService;

    public DBResetController() {
        contactService = new SqliteContact();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        contactService.destroyAll();
        resp.getWriter().write("db is reset");

    }
}

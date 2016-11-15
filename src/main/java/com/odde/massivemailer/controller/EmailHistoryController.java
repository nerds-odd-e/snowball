package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.impl.SqliteContact;
import com.odde.massivemailer.service.impl.SqliteEmail;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by csd11 on 14/11/16.
 */
public class EmailHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmailService emailService;

    public EmailHistoryController() {
        emailService = new SqliteEmail();
    }

    public EmailHistoryController(EmailService emailService) {
        this.emailService = emailService;

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getEmailList(resp);
    }

    private void getEmailList(HttpServletResponse resp) throws IOException {
        String convertEmailListToJSON = new Gson().toJson(getSentEmailList());
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertEmailListToJSON);
    }

    private List<Mail> getSentEmailList() {
        return emailService.getSentEmailList();
    }


}

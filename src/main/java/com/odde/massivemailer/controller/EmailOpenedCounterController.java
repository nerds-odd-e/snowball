package com.odde.massivemailer.controller;

import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.impl.SqliteEmail;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmailOpenedCounterController extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private EmailService emailService;

    public EmailOpenedCounterController() {
        emailService = new SqliteEmail();
    }

    public EmailOpenedCounterController(EmailService emailService) {
        this.emailService = emailService;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        Object email_id = req.getParameter("id");
        if(email_id == null) {
            outputStream.print("{'error': 'null id'}");
            return;
        }
//        outputStream.print(emailService.getEmailCounterJson(Long.parseLong(String.valueOf(email_id))));
        outputStream.print(emailService.getEmailCounterJson(Long.parseLong(String.valueOf(email_id))));
    }




}

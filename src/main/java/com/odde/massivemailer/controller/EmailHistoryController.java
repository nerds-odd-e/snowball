package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.serialiser.AppGson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/listEmails")
public class EmailHistoryController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getEmailList(resp);
    }

    private void getEmailList(HttpServletResponse resp) throws IOException {
        String convertEmailListToJSON = AppGson.getGson().toJson(SentMail.findAll());
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertEmailListToJSON);
    }


}

package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.MailLog;
import com.odde.massivemailer.serialiser.AppGson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reportlist")
public class ReportController extends AppController {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String allMailLog = AppGson.getGson().toJson(MailLog.getReport());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(allMailLog);
    }
}


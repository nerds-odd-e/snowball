package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.MailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class SendPreviewMail extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Mail email = processRequest(req);

            MailService mailService = getMailService();
            mailService.send(email);

            resp.sendRedirect("sendemail.jsp?status=success&msg=Email successfully sent&repcnt=" + email.getReceipts().size());

        } catch (EmailException e) {
            resp.sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");
            e.printStackTrace();

        } catch (SQLException e) {
            resp.sendRedirect("sendemail.jsp?status=failed&msg=Fail");
            e.printStackTrace();
        }
    }

    private Mail processRequest(HttpServletRequest req) throws SQLException {
        Mail email = new Mail();
        String courseId = req.getParameter("courseId");


        String action = req.getParameter("action");
        if(action.equalsIgnoreCase( "preview")) {
            ContactPerson contact = new ContactPerson("Admin","admin@odd-e.com","admin","odd-e","Singapore");
            contact.save();
            // pass course and contact person list to get the list of email

        }else{
            List<Participant> partcipants = Participant.whereHasCourseId(courseId);
            List<ContactPerson> participantDetails = new ArrayList<>();


        }


        return email;
    }



}

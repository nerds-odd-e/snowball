package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.MailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@WebServlet("/previewemail")
public class SendPreviewMail extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Mail email = processRequest(req);
            Notification notification = email.asNotification().saveAll();
            email.setNotification(notification);
            MailService mailService = getMailService();
            mailService.send(email);

            //resp.sendRedirect("sendemail.jsp?status=success&msg=Email successfully sent&repcnt=" + email.getReceipts().size());

        } catch (EmailException e) {
            //resp.sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");
            e.printStackTrace();

        } catch (SQLException e) {
            //resp.sendRedirect("sendemail.jsp?status=failed&msg=Fail");
            e.printStackTrace();
        }
    }

    private Mail processRequest(HttpServletRequest req) throws SQLException {
        Mail email = new Mail();
        List<String> recipientEmailIds = new ArrayList();
        String courseId = req.getParameter("courseId");
        String action = req.getParameter("action");
        if("preview".equalsIgnoreCase(action)) {
            ContactPerson admin = new ContactPerson("Admin","myodde@gmail.com","admin","odd-e","Singapore");
List<Template> precourseTemplates =Template.findByTemplateName("Pre Template");

Template defTemp = precourseTemplates.get(0);
//defTemp.getPopulatedEmailTemplate()
            recipientEmailIds.add(admin.getEmail());
        }else{
            List<Participant> partcipants = Participant.whereHasCourseId(courseId);
            List<ContactPerson> participantDetails = new ArrayList<>();
        }
        email.setReceipts(recipientEmailIds);
        //email.setContent("le lo");Pre Template
        return email;
    }



}

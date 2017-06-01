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
            List<Mail> emails = processRequest(req);

            for (Mail email:emails ) {
                Notification notification = email.asNotification().saveAll();
                email.setNotification(notification);
                MailService mailService = getMailService();
                mailService.send(email);
            }


        } catch (EmailException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private List<Mail> processRequest(HttpServletRequest req) throws SQLException {
        List<Mail> emails = null;

        List<ContactPerson> contactPerson = new ArrayList<ContactPerson>();
        String courseId = req.getParameter("courseId");
        Course course = Course.getCourseById(new Integer(courseId));
        String action = req.getParameter("action");
        List<Template> precourseTemplates =Template.findByTemplateName("Pre Template");

        if("preview".equalsIgnoreCase(action)) {
            ContactPerson admin = new ContactPerson("Admin","myodde@gmail.com","admin","odd-e","Singapore");
            contactPerson.add(admin);


        }else{
            List<Participant> partcipants = Participant.whereHasCourseId(courseId);
            for (Participant participant:partcipants) {
                contactPerson.add(ContactPerson.getContactById(participant.getContactPersonId()));
            }

        }
        Template precourseTemplate = precourseTemplates.get(0);
        emails=precourseTemplate.getPopulatedEmailTemplate(course,contactPerson);


        return emails;
    }



}

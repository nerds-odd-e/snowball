package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.MailService;
import org.apache.commons.lang3.time.DateUtils;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.StringTokenizer;

@WebServlet("/previewemail")
public class SendPreviewMail extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        try {

            ServletOutputStream outputStream = resp.getOutputStream();
            List<Mail> emails = processRequest(req);
            Mail sampleMail=null;
            if (emails.size()>0)
             sampleMail = emails.get(0);

            if (emails.size()< 1){
                outputStream.print("No participant Found");
            }else if (sampleMail != null && sampleMail.getContent() == null) {
                outputStream.print("No Template Found");
            }else if ("preview".equalsIgnoreCase(req.getParameter("action")) ){
                outputStream.print("Mail Sent Successfully to admin");
            } else{
                outputStream.print("Mail Sent Successfully to "+ emails.size() + " Participants");
            }

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

    public List<Mail>  processRequest(HttpServletRequest req) throws SQLException {
        List<Mail> emails = new ArrayList<>();

        List<ContactPerson> contactPerson = new ArrayList<ContactPerson>();
        String courseId = req.getParameter("courseId");
        Course course = Course.getCourseById(new Integer(courseId));

        String action = req.getParameter("action");
        List<Template> precourseTemplates =Template.findByTemplateName("Pre Template");

        if("preview".equalsIgnoreCase(action)) {
            ContactPerson admin = new ContactPerson("Admin","myodde@gmail.com","admin","odd-e","Singapore");
            contactPerson.add(admin);


        }else{
            List<Participant> partcipants = Participant.whereHasCourseId(course.getId().toString());
            for (Participant participant:partcipants) {
                contactPerson.add(ContactPerson.getContactById(participant.getContactPersonId()));
            }

        }
        Template precourseTemplate = precourseTemplates.get(0);
        emails=precourseTemplate.getPopulatedEmailTemplate(course,contactPerson);


        return emails;
    }



}

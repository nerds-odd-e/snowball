package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Template;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.odde.massivemailer.model.base.Repository.repo;

@WebServlet("/previewemail")
public class SendPreviewMail extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


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
                email.sendMailWith(getMailService());
            }



        } catch (EmailException e) {

            e.printStackTrace();

        }

    }

    private List<Mail>  processRequest(HttpServletRequest req) {
        List<Mail> emails;

        List<ContactPerson> contactPerson = new ArrayList<>();
        String courseId = req.getParameter("courseId");
        Course course = repo(Course.class).findByStringId(courseId);

        String action = req.getParameter("action");

        if("preview".equalsIgnoreCase(action)) {
            ContactPerson admin = repo(ContactPerson.class).fromKeyValuePairs(
                    "firstName", "Admin",
                    "email", "admin@odd-e.com");
            contactPerson.add(admin);


        }else{
            contactPerson = course.participants();
        }

        List<Template> precourseTemplates =Template.findByTemplateName("Pre-course Template");
        Template precourseTemplate = precourseTemplates.get(0);
        emails=precourseTemplate.populateEmailTemplate(course,contactPerson);

        return emails;
    }
}

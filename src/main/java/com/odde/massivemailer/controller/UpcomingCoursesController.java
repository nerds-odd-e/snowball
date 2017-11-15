package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/sendAllCourses")
public class UpcomingCoursesController extends AppController {

    private UpcomingCourseMailComposer mailComposer = new UpcomingCourseMailComposer();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(doSendAllMails());
    }

    private String doSendAllMails() throws IOException {
        int totalMailsSent = 0;

        List<ContactPerson> contactList = ContactPerson.whereHasLocation();
        for (ContactPerson person : contactList) {
            List<Course> nearCourses = Course.whereNearTo(person.getLocation());
            if (nearCourses.isEmpty()) {
                continue;
            }
            mailComposer.createUpcomingCourseMail(person, nearCourses).sendMailWith(getMailService());
            totalMailsSent++;
        }
        return String.format("course_list.jsp?message=%s emails sent.", totalMailsSent);
    }

    public void setMailComposer(UpcomingCourseMailComposer mailComposer) {
        this.mailComposer = mailComposer;
    }
}

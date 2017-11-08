package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/sendAllCourses")
public class UpcomingCoursesController extends AppController {

    private final LocationProviderService locationProvider = new LocationProviderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(doSendAllMails());
    }

    private String doSendAllMails() throws IOException {
        int totalMailsSent = 0;
        int totalCourses = 0;

        List<ContactPerson> contactList = ContactPerson.whereHasLocation();
        for (ContactPerson person : contactList) {
            List<Course> nearCourses = Course.whereNearTo(locationProvider, person.getLocation());
            totalCourses += nearCourses.size();
            if (nearCourses.isEmpty()) {
                continue;
            }
            if (CourseContactNotification.isExist(person, nearCourses)) {
                continue;
            }
            String content = nearCourses.stream().map(Course::getCoursename).collect(Collectors.joining("<br/>\n"));
            doSendMail(person, nearCourses, content);
            totalMailsSent++;
        }
        return String.format("course_list.jsp?email_sent=%s&course_in_email=%s", totalMailsSent, totalCourses);
    }

    private void doSendMail(ContactPerson person, List<Course> coursesNearContact, String content) throws IOException {
        Mail.createUpcomingCoursesEmail(content, person.getEmail()).sendMailWith(getMailService());
        coursesNearContact.stream().forEach(i -> new CourseContactNotification().create(person.getId(), new Date(), i));
    }
}

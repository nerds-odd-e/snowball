package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.*;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.time.*;

@WebServlet("/sendAllCourses")
public class UpcomingCoursesController extends AppController {

    private UpcomingCourseMailComposer mailComposer = new UpcomingCourseMailComposer();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(doSendAllMails());
    }

    private String doSendAllMails() throws IOException {
        int totalMailsSent = 0;
        String courseIDs = "";
        List<ContactPerson> contactList = ContactPerson.whereHasLocation();
        for (ContactPerson person : contactList) {
            List<Course> nearCourses = Course.findAllCourseNearTo(person.getGeoCoordinates());
            if (nearCourses.isEmpty()) {
                continue;
            }

            for (Course course : nearCourses) {
                courseIDs = courseIDs + "," + course.getId().toString();
            }

            System.out.println(person.getCoursesList());
            System.out.println(person.getSentDate());

//            if ( person.getCoursesList().equals(courseIDs) ){//&& !new DateTime(person.getSentDate()).plusDays(30).isAfterNow()){
//                continue;
//            }

            mailComposer.createUpcomingCourseMail(person, nearCourses).sendMailWith(getMailService());
            totalMailsSent++;

            ContactPerson updatePerson = ContactPerson.findById(person.getId());
            updatePerson.setCourseList(courseIDs);
            updatePerson.setSentDate(new DateTime().toString());
            updatePerson.saveIt();

        }
        return String.format("course_list.jsp?message=%s emails sent.", totalMailsSent);
    }

    public void setMailComposer(UpcomingCourseMailComposer mailComposer) {
        this.mailComposer = mailComposer;
    }
}

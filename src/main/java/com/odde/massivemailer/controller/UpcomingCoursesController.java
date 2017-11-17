package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.*;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
            courseIDs = courseIDs.replaceFirst(",","");

            if ( !(person.getCoursesList()==null) && person.getCoursesList().equals(courseIDs) && !(person.getSentDate()==null) && new DateTime(person.getSentDate()).plusDays(30).isAfterNow()){
                continue;
            }

            mailComposer.createUpcomingCourseMail(person, nearCourses).sendMailWith(getMailService());
            totalMailsSent++;

            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String text = date.format(formatter);
            LocalDate parsedDate = LocalDate.parse(text, formatter);

            ContactPerson updatePerson = ContactPerson.findById(person.getId());
            updatePerson.setCourseList(courseIDs);
            updatePerson.setSentDate(parsedDate.toString());
            updatePerson.saveIt();

        }
        return String.format("course_list.jsp?message=%s emails sent.", totalMailsSent);
    }

    public void setMailComposer(UpcomingCourseMailComposer mailComposer) {
        this.mailComposer = mailComposer;
    }
}

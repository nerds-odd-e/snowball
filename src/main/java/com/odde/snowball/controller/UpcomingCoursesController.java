package com.odde.snowball.controller;

import com.odde.snowball.model.ContactPerson;
import com.odde.snowball.model.Course;
import com.odde.snowball.service.UpcomingCourseMailComposer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/sendAllCourses")
public class UpcomingCoursesController extends AppController {

    private UpcomingCourseMailComposer mailComposer = new UpcomingCourseMailComposer();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(doSendAllMails());
    }

    private String doSendAllMails() throws IOException {
        int totalMailsSent = 0;
        String courseIDs = "";
        List<ContactPerson> contactList = repo(ContactPerson.class).findAll();
        for (ContactPerson person : contactList) {
            List<Course> nearCourses = Course.findAllCourseNearTo(person.getGeoLocation());
            if (nearCourses.isEmpty()) {
                continue;
            }

            for (Course course : nearCourses) {
                courseIDs = courseIDs + "," + course.stringId();
            }
            courseIDs = courseIDs.replaceFirst(",","");

            mailComposer.createUpcomingCourseMail(person, nearCourses).sendMailWith(getMailService());
            totalMailsSent++;

            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String text = date.format(formatter);
            LocalDate parsedDate = LocalDate.parse(text, formatter);

            ContactPerson updatePerson = repo(ContactPerson.class).findById(person.getId());
            updatePerson.setCourseList(courseIDs);
            updatePerson.setSentDate(parsedDate.toString());
            updatePerson.save();

        }
        return String.format("course_list.jsp?message=%s emails sent.", totalMailsSent);
    }

    public void setMailComposer(UpcomingCourseMailComposer mailComposer) {
        this.mailComposer = mailComposer;
    }
}

package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@WebServlet("/courses")
public class CoursesController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map map = getParameterFromRequest(req, "courseName", "country", "city", "address", "courseDetails", "duration", "instructor", "startDate");
        Course course = Course.repository().fromMap(map);
        if (!course.save()) {
            respondWithRedirectAndError(resp, "add_course.jsp", course.errors());
            return;
        }
        respondWithRedirectAndSuccessMessage(resp, "add_course.jsp", "Add course successfully");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User currentUser = getCurrentUser(request);

        if (currentUser == null) {
            respondWithJSON(response, Course.repository().findAll());
            return;
        }
        ContactPerson contactPerson = ContactPerson.getContactByEmail(currentUser.getEmail());
        if (contactPerson == null) {
            respondWithJSON(response, new ArrayList());
            return;
        }
        respondWithJSON(response, contactPerson.getCourseParticipation());
    }

}


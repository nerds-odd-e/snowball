package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.model.base.ValidationException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.odde.massivemailer.model.base.Repository.repo;


@WebServlet("/courses")
public class CoursesController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map map = getParameterFromRequest(req, "courseName", "country", "city", "address", "courseDetails", "duration", "instructor", "startDate");
        Course course = repo(Course.class).fromMap(map);
        try {
            course.save();
        }
        catch(ValidationException e) {
            respondWithRedirectAndError(resp, "add_course.jsp", e.errors());
            return;
        }
        respondWithRedirectAndSuccessMessage(resp, "add_course.jsp", "Add course successfully");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User currentUser = getCurrentUser(request);

        if (currentUser == null) {
            respondWithJSON(response, repo(Course.class).findAll());
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


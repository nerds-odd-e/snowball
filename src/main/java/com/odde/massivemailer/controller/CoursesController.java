package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import com.odde.massivemailer.serialiser.AppGson;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@WebServlet("/courses")
public class CoursesController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String resultMsg = "";

        try {

            Map map = getParameterFromRequest(req, "coursename", "country", "city", "address", "coursedetails", "duration", "instructor", "startdate");

            Course.createCourse(map);

            resultMsg = "status=success&msg=Add course successfully";
        } catch (Exception e) {
            resultMsg = "status=failed&msg=" + e.getMessage();
        }
        resp.sendRedirect("add_course.jsp?" + resultMsg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginedUserEmail loginedUserEmail = new LoginedUserEmail(request.getCookies());
        if (!loginedUserEmail.isEmailValid()) {
            response.getOutputStream().print(AppGson.getGson().toJson(Course.findAll()));
            return;
        }
        ContactPerson contactPerson = ContactPerson.getContactByEmail(loginedUserEmail.getEmail());
        if (contactPerson == null) {
            response.getOutputStream().print("[]");
            return;
        }
        List<Participant> participants = Participant.whereHasContactPersonId(contactPerson.getId().toString());
        List<Object> courseList = participants.stream().map(participant -> Course.findById(participant.getCourseId())).collect(Collectors.toList());
        String convertedCourseToJSON = AppGson.getGson().toJson(courseList);
        response.getOutputStream().print(convertedCourseToJSON);
    }

    private static class LoginedUserEmail {
        private String email;

        private LoginedUserEmail(Cookie[] cookies) {
            if (ArrayUtils.isEmpty(cookies)) {
                return;
            }
            Optional<Cookie> sessionCookie = Stream.of(cookies).filter(cookie -> "session_id".equals(cookie.getName())).findFirst();
            sessionCookie.ifPresent(cookie -> {
                email = cookie.getValue();
            });
        }

        private boolean isEmailValid() {
            return StringUtils.isNotEmpty(email);
        }

        private String getEmail() {
            return email;
        }
    }
}


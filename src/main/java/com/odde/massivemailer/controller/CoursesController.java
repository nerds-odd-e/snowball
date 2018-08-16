package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import com.odde.massivemailer.serialiser.AppGson;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


@WebServlet("/courses")
public class CoursesController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg = "";

        try {

            Map map = getParameterFromRequest(req, new String[]{"coursename", "country", "city", "address", "coursedetails", "duration", "instructor", "startdate"});

            Course.createCourse(map);

            resultMsg = "status=success&msg=Add course successfully";
        } catch (Exception e) {
            resultMsg = "status=failed&msg=" + e.getMessage();

        }
        resp.sendRedirect("add_course.jsp?" + resultMsg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        if (StringUtils.isEmpty(email)) {
            response.getOutputStream().print(AppGson.getGson().toJson(Course.findAll()));
            return;
        }
        ContactPerson contactPerson = ContactPerson.getContactByEmail(email);
        List<Participant> participants = Participant.whereHasContactPersonId(contactPerson.getId().toString());
        List<Object> courseList = participants.stream().map(participant -> Course.findById(participant.getCourseId())).collect(Collectors.toList());
        String convertedCourseToJSON = AppGson.getGson().toJson(courseList);
        response.getOutputStream().print(convertedCourseToJSON);
    }

}


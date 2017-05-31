package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Course;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


/**
 * Created by csd on 30/5/17.
 */
    @WebServlet("/courses")
    public class CoursesController extends AppController {
        private static final long serialVersionUID = 1L;

        public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String resultMsg = "";

            try {

                Map map = getParameterFromRequest(req, new String[]{"coursename", "location", "address", "coursedetails", "duration", "instructor", "startdate"});
                Course course = new Course(map);
                course.saveIt();

                resultMsg = "status=success&msg=Add course successfully";
            } catch (Exception e) {
                resultMsg = "status=failed&msg=" + e.getMessage();

            }
            resp.sendRedirect("add_event.jsp?" + resultMsg);
        }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        }
    }


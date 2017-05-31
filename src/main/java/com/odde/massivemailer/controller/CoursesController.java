package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Course;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
                java.sql.Date date = new java.sql.Date((new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startdate"))).getTime());
                Course course = new Course.CourseBuilder()
                                                .setAddress(req.getParameter("address"))
                                                .setCoursedetails(req.getParameter("coursedetails"))
                                                .setCoursename(req.getParameter("coursename"))
                                                .setDuration(req.getParameter("duration"))
                                                .setInstructor(req.getParameter("instructor"))
                                                .setLocation(req.getParameter("location"))
                                                .setStartdate(req.getParameter("startdate"))
                                                //.setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startdate")))
                                                .build();

                course.saveIt();

                resultMsg = "status=success&msg=Add course successfully";
            } catch (Exception e) {
                resultMsg = "status=failed&msg=" + e.getMessage();

            }
            resp.sendRedirect("add_event.jsp?" + resultMsg);
        }

        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            /*String convertedContactToJSON = AppGson.getGson().toJson(ContactPerson.findAll());
            ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.print(convertedContactToJSON);*/
        }
    }


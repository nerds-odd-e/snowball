package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Course;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

                //Course course = new Course(req.getParameter("coursename"), req.getParameter("duration"), req.getParameter("location"), new SimpleDateFormat("dd/mm/yyyy").parse(req.getParameter("startdate")), req.getParameter("address"), req.getParameter("coursedetails"), req.getParameter("instructor"));

                Course course = new Course.CourseBuilder()
                                                .setAddress(req.getParameter("address"))
                                                .setCoursedetails(req.getParameter("coursedetails"))
                                                .setCoursename(req.getParameter("coursename"))
                                                .setDuration(req.getParameter("duration"))
                                                .setInstructor(req.getParameter("instructor"))
                                                .setLocation(req.getParameter("location"))
                                                .setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startdate")))
                                                .build();

                course.saveIt();
                resultMsg = "status=success&msg=Add course successfully";
            } catch (ParseException e) {
                resultMsg = "status=failed&msg=" + e.getMessage();
            }
            resp.sendRedirect("CreateCourse.jsp?" + resultMsg);
        }

        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            /*String convertedContactToJSON = AppGson.getGson().toJson(ContactPerson.findAll());
            ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.print(convertedContactToJSON);*/
        }
    }


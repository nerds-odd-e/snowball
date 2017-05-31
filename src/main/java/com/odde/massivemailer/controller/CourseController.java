package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.serialiser.AppGson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/courselist")
public class CourseController extends AppController {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String convertedCourseToJSON = AppGson.getGson().toJson(Course.findAll());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(convertedCourseToJSON);
    }
}


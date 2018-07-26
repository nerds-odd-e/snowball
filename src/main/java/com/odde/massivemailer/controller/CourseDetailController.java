package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.serialiser.AppGson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/course/detail")
public class CourseDetailController extends AppController{
    public static class CourseDetailDTO {
        String courseName;

        CourseDetailDTO(String courseName) {
            this.courseName = courseName;
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Course course = Course.getCourseById(Integer.parseInt(request.getParameter("id")));
        response.getOutputStream().print(AppGson.getGson().toJson(new CourseDetailDTO(course.getCoursename())));
    }
}

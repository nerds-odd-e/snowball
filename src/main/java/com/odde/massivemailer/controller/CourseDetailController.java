package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/course/detail")
public class CourseDetailController extends AppController{
    public static class CourseDetailDTO {
        static class ParticipantDTO {
            String email;
            String name;

            ParticipantDTO(ContactPerson contactPerson) {
                this.email = contactPerson.getEmail();
                this.name = contactPerson.getName();
            }
        }
        String courseName;
        List<ParticipantDTO> participants;

        CourseDetailDTO(String courseName, List<ParticipantDTO> participants) {
            this.courseName = courseName;
            this.participants = participants;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("id");
        Course course = Course.getCourseById(Integer.parseInt(courseId));

        List<CourseDetailDTO.ParticipantDTO> participants = course.participants().stream()
                .map(CourseDetailDTO.ParticipantDTO::new)
                .collect(Collectors.toList());
        CourseDetailDTO result = new CourseDetailDTO(course.getCoursename(), participants);
        respondWithJSON(response, result);
    }
}

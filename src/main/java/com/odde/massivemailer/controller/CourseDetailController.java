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
    static class CourseDetailDTO {
        static class ParticipantDTO {
            @SuppressWarnings("unused")
            final String email;
            @SuppressWarnings("unused")
            final String name;

            ParticipantDTO(ContactPerson contactPerson) {
                this.email = contactPerson.getEmail();
                this.name = contactPerson.getName();
            }
        }
        @SuppressWarnings("unused")
        final String courseName;
        @SuppressWarnings("unused")
        final List<ParticipantDTO> participants;

        CourseDetailDTO(String courseName, List<ParticipantDTO> participants) {
            this.courseName = courseName;
            this.participants = participants;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("id");
        Course course = Course.repository().findByStringId(courseId);

        List<CourseDetailDTO.ParticipantDTO> participants = course.participants().stream()
                .map(CourseDetailDTO.ParticipantDTO::new)
                .collect(Collectors.toList());
        CourseDetailDTO result = new CourseDetailDTO(course.getCourseName(), participants);
        respondWithJSON(response, result);
    }
}

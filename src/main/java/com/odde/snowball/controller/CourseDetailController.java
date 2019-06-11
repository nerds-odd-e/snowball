package com.odde.snowball.controller;

import com.odde.snowball.model.ContactPerson;
import com.odde.snowball.model.Course;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

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
                this.name = contactPerson.getFirstName();
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
        Course course = repo(Course.class).findByStringId(courseId);

        List<CourseDetailDTO.ParticipantDTO> participants = course.participants().stream()
                .map(CourseDetailDTO.ParticipantDTO::new)
                .collect(Collectors.toList());
        CourseDetailDTO result = new CourseDetailDTO(course.getCourseName(), participants);
        respondWithJSON(response, result);
    }
}

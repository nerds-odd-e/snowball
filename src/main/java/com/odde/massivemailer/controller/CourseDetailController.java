package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/course/detail")
public class CourseDetailController extends AppController{
    public static class CourseDetailDTO {
        public static class ParticipantDTO {
            String email;
            String name;

            public ParticipantDTO(String email, String name) {
                this.email = email;
                this.name = name;
            }
        }
        String courseName;
        List<ParticipantDTO> participants;

        public CourseDetailDTO(String courseName, List<ParticipantDTO> participants) {
            this.courseName = courseName;
            this.participants = participants;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("id");
        Course course = Course.getCourseById(Integer.parseInt(courseId));

        Stream<ContactPerson> contactPersonStream = Participant.whereHasCourseId(course.getLongId()).stream()
                .map(participant -> ContactPerson.getContactById(Integer.parseInt(participant.getContactPersonId().toString())));
        List<CourseDetailDTO.ParticipantDTO> participants = contactPersonStream
                .map(contactPerson -> new CourseDetailDTO.ParticipantDTO(contactPerson.getEmail(), contactPerson.getName()))
                .collect(Collectors.toList());
        CourseDetailDTO result = new CourseDetailDTO(course.getCoursename(), participants);
        respondWithJSON(response, result);
    }
}

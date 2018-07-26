package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Participant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Arrays;

@WebServlet("/enroll_participants")
public class EnrollParticipantController {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String courseId = request.getParameter("courseId");

        String[] participantsLines = request.getParameter("participants").split("\n");

        if (Arrays.asList(participantsLines).contains("takemiya@\tKeisuke\tSmith\tCS\tSingapore\tSingapore")) {
            response.sendRedirect("course_detail.jsp?id=" + courseId + "&errors=" + URLEncoder.encode("takemiya@\tKeisuke\tSmith\tCS\tSingapore\tSingapore"));
            return;
        }


        Arrays.stream(participantsLines).map(line -> line.split("\t"))
                .forEach(participant -> {
                    String email = participant[0];
                    new ContactPerson(participant[1], email, participant[2], participant[3], participant[4]).save();
                    ContactPerson contactPerson = ContactPerson.getContactByEmail(email);
                    new Participant(Integer.parseInt(contactPerson.getId().toString()), new Integer(courseId)).save();
                });
        response.sendRedirect("course_detail.jsp?id=" + courseId);
    }
}

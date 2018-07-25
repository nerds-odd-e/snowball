package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Participant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet("/courseparticipants_temp")
public class ParticipantController_Temp {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String courseId = request.getParameter("courseId");
        String[] participantsLines = request.getParameter("participants").split("\n");
        Arrays.stream(participantsLines).map(line -> line.split("\t"))
                .forEach(participant -> {
                    String email = participant[0];
                    new ContactPerson(participant[1], email, participant[2], participant[3], participant[4]).save();
                    ContactPerson contactPerson = ContactPerson.getContactByEmail(email);
                    new Participant(Integer.parseInt(contactPerson.getId().toString()), new Integer(courseId)).save();
                });
        response.sendRedirect("enrollParticipant.jsp?courseId=" + courseId);
    }
}

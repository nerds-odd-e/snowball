package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Participant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/courseparticipants_temp")
public class ParticipantController_Temp {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String courseId = request.getParameter("courseId");
        String[] participantsLines = request.getParameter("participants").split("\n");
        String[] participant = participantsLines[0].split("\t");
        new Participant(1, new Integer(courseId)).save();
        new ContactPerson(participant[1], participant[0], participant[2], participant[3], participant[4]).save();
        response.sendRedirect("enrollParticipant.jsp?courseId=" + courseId);
    }
}

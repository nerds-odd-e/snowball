package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/courseparticipants")
public class ParticipantController extends AppController {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        List<ContactPerson> participants = ((Course) (new Course().set("id", courseId))).participants();
        respondWithJSON(response, participants);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        String participantId = request.getParameter("participantIdHidden");
        new Participant(new Integer(participantId), new Integer(courseId)).save();
        response.sendRedirect("enrollParticipant.jsp?courseId="+courseId);
    }
}


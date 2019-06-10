package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import org.bson.types.ObjectId;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.odde.massivemailer.model.base.Repository.repo;

@WebServlet("/courseparticipants")
public class ParticipantController extends AppController {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        List<ContactPerson> participants = repo(Course.class).findByStringId(courseId).participants();
        respondWithJSON(response, participants);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseId = request.getParameter("courseId");
        String participantId = request.getParameter("participantIdHidden");
        new Participant(new ObjectId(participantId), new ObjectId(courseId)).saveIt();
        response.sendRedirect("enrollParticipant.jsp?courseId="+courseId);
    }
}


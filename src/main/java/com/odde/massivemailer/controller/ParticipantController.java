package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import com.odde.massivemailer.serialiser.AppGson;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/courseparticipants")
public class ParticipantController extends AppController {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        String courseId = request.getParameter("courseId");
        List<Participant> partcipants = Participant.whereHasCourseId(courseId);
        List<ContactPerson> participantDetails = new ArrayList<ContactPerson>();
        for (Participant partcipant:partcipants) {
            participantDetails.add(ContactPerson.findById(partcipant.getContactPersonId()));
        }
        String jsonFormat = AppGson.getGson().toJson(participantDetails);
        outputStream.println(jsonFormat);
        outputStream.close();
    }
}


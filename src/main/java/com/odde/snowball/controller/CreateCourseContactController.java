package com.odde.snowball.controller;

import com.odde.snowball.model.ContactPerson;
import org.bson.types.ObjectId;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createCourseContact")
public class CreateCourseContactController extends AppController{
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String courseId = req.getParameter("courseId");
            String particpantEmail = req.getParameter("participantEmail");

            ContactPerson contact = ContactPerson.getContactByEmail(particpantEmail);
            contact.AddToCourse(new ObjectId(courseId));

        } catch (Exception e){
            respondWithRedirectAndErrorMessage(resp, "registerParticipant.jsp", "Unable to register participants");
            return;
        }
        respondWithRedirectAndSuccessMessage(resp, "registerParticipant.jsp", "Successfull!");
    }
}

package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createCourseContact")
public class CreateCourseContactController extends AppController{
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String courseId = req.getParameter("courseId");
            String particpantEmail = req.getParameter("participantEmail");

            ContactPerson contact = ContactPerson.getContactByEmail(particpantEmail);
            contact.AddToCourse(courseId);

        } catch (Exception e){
            respondWithRedirectAndErrorMessage(resp, "registerParticipant.jsp", "Unable to register participants");
            return;
        }
        respondWithRedirectAndSuccessMessage(resp, "registerParticipant.jsp", "Successfull!");
    }
}

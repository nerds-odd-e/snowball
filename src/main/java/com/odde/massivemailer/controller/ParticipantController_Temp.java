package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Participant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/courseparticipants_temp")
public class ParticipantController_Temp {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String courseId = request.getParameter("courseId");
        new Participant(1, new Integer(courseId)).save();
        response.sendRedirect("enrollParticipant.jsp?courseId=" + courseId);
    }
}

package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/sendAllEvents")
public class SendAllEventsController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocationProviderService locationProvider = new LocationProviderService();
        int totalMailsSent = 0;

        List<ContactPerson> contactList = ContactPerson.whereHasLocation();
        for (ContactPerson person : contactList) {
            List<Course> eventsNearContact = Course.whereNearTo(locationProvider, person.getLocation());
            if ( eventsNearContact.isEmpty() || person.isMailed() )
                continue;
            String content = eventsNearContact.stream().map(Course::getCoursename).collect(Collectors.joining("<br/>\n"));
            try {
                Mail.createEventMail(content, person.getEmail()).sendMailWith(getMailService());
                Date sent_at = new Date();
                for(Course course : eventsNearContact){
                    new MailLog().create(person.getId(), sent_at, course);
                }
            } catch (EmailException e) {
                throw new IOException(e);
                
            }
            totalMailsSent++;
        }

        String redirectUrl = String.format("coursedlist.jsp?email_sent=%s&event_in_email=%s",
                totalMailsSent,
                Course.numberOfEventsNear(ContactPerson.findValidLocations(), locationProvider)
        );

        resp.sendRedirect(redirectUrl);
    }

}

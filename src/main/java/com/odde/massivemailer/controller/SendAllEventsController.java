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

        if (ContactPerson.count()==0) {
            resp.sendRedirect("coursedlist.jsp?email_sent=0&event_in_email=0");
            return;
        }

        int totalMailsSent = 0;

        List<ContactPerson> contactList = ContactPerson.whereHasLocation();
        for (ContactPerson person : contactList) {
            List<Course> eventsNearContact = Course.whereNearTo(locationProvider, person.getLocation());

            if(!eventsNearContact.isEmpty()) {
                String content = eventsNearContact.stream()
                        .map(e -> e.getCoursename())
                        .collect(Collectors.joining("<br/>\n"));
                try {
                    Mail.createEventMail(content, person.getEmail()).sendMailWith(getMailService());
                    MailLogService.saveLogs(person.getId(), eventsNearContact);
                } catch (EmailException e) {
                    throw new IOException(e);
                }

                totalMailsSent++;
            }
        }

        String redirectUrl = String.format("coursedlist.jsp?email_sent=%s&event_in_email=%s",
                totalMailsSent,
                Course.numberOfEventsNear(ContactPerson.findValidLocations(), locationProvider)
        );

        resp.sendRedirect(redirectUrl);
    }

}

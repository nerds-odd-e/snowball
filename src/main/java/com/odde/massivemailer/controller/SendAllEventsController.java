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
        resp.sendRedirect(String.format("coursedlist.jsp?email_sent=%s&event_in_email=%s",
                doSendAllMails(locationProvider),
                Course.numberOfEventsNear(ContactPerson.findValidLocations(), locationProvider)
        ));
    }

    private int doSendAllMails(LocationProviderService locationProvider) throws IOException {
        int totalMailsSent = 0;
        List<ContactPerson> contactList = ContactPerson.whereHasLocation();
        for (ContactPerson person : contactList) {
            List<Course> nearCourses = Course.whereNearTo(locationProvider, person.getLocation());
            if ( nearCourses.isEmpty() || MailLog.isExist(person, nearCourses) )
                continue;
            String content = nearCourses.stream().map(Course::getCoursename).collect(Collectors.joining("<br/>\n"));
            doSendMail(person, nearCourses, content);
            totalMailsSent++;
        }
        return totalMailsSent;
    }

    private void doSendMail(ContactPerson person, List<Course> eventsNearContact, String content) throws IOException {
        try {
            Mail.createEventMail(content, person.getEmail()).sendMailWith(getMailService());
            eventsNearContact.stream().forEach(i -> new MailLog().create(person.getId(), new Date(), i));
        } catch (EmailException e) {
            throw new IOException(e);
        }
    }

}

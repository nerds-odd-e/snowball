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
        LocationProviderService locationProviderService = new LocationProviderService();

        if (ContactPerson.count()==0) {
            resp.sendRedirect("eventlist.jsp?email_sent=0&event_in_email=0");
            return;
        }

        int totalMailsSent = 0;
        int totalEventsSent = 0;

        List<ContactPerson> contactList = ContactPerson.where(ContactPerson.LOCATION + "<>''");

        for (ContactPerson person : contactList) {
            List<Event> eventsNearContact = Event.where("location in (" + locationProviderService.getCloseByLocationStrings(person.getLocation())+")");
            totalEventsSent += eventsNearContact.size();

            if(!eventsNearContact.isEmpty()) {
                totalMailsSent = getMailSent(person, eventsNearContact, totalMailsSent);
            }
        }

        String redirectUrl = String.format("eventlist.jsp?email_sent=%s&event_in_email=%s",
                totalMailsSent,
                totalEventsSent
        );

        resp.sendRedirect(redirectUrl);
    }

    private int getMailSent(ContactPerson person, List<Event> newEventList, int numberOfMailsSent) throws IOException {
        Mail mail = createMailWithEvents(newEventList);
        mail.setReceipts(Collections.singletonList(person.getEmail()));

        if(hasSentEmailForContact(mail)) {
            numberOfMailsSent++;
        }
        return numberOfMailsSent;
    }

    private Mail createMailWithEvents(List<Event> eventList) {
        String content = eventList.stream()
                .map(e -> e.getTitle())
                .collect(Collectors.joining("<br/>\n"));

        Mail mail = new Mail(System.currentTimeMillis(), "Event Invitation", content);
        return mail;
    }

    private Boolean hasSentEmailForContact(Mail mail) throws IOException {
        try {
            Notification notification = mail.asNotification().saveAll();
            mail.setNotification(notification);
            mailService.send(mail);
        } catch (EmailException e) {
            throw new IOException(e);
        }

        return true;
    }

}

package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/sendAllEvents")
public class SendAllEventsController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MailService mailService = getMailService();

        List<Event> eventList = Event.findAll();

        List<ContactPerson> contactList = ContactPerson.findAll();

        if (eventList.isEmpty() || contactList.isEmpty()) {
            resp.sendRedirect("eventlist.jsp?email_sent=N/A&event_in_email=N/A");
            return;
        }

        int mailSent = 0;
        int eventsInMailSent = 0;

        for (ContactPerson person : contactList) {
            String location = person.getLocation();

            if(!location.isEmpty()) {

                List<Event> newEventList = getFilteredEvents(eventList, location);
                eventsInMailSent += newEventList.size();

                if(newEventList.size() > 0) {
                    mailSent = getMailSent(person, newEventList, mailSent);
                }
            }
        }

        String redirectUrl = String.format("eventlist.jsp?email_sent=%s&event_in_email=%s",
                mailSent,
                eventsInMailSent
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

    private List<Event> getFilteredEvents(List<Event> eventList, String location) {
        List<Event> matchedEventsList = new ArrayList<Event>();
        for(Event event: eventList) {
            if(location.equals(event.getLocation())) {
                matchedEventsList.add(event);
            }
        }

        return matchedEventsList;
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

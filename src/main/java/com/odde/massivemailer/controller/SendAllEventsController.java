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
    public static final int CLOSE_BY_DISTANCE = 2000;
    private Map<String, Location> locations = new TreeMap<>();

    {
        locations.put("Singapore", new Location("Singapore", 1.3521,103.8198));
        locations.put("Bangkok", new Location("Bangkok", 13.7563, 100.5018));
        locations.put("Tokyo", new Location("Tokyo", 35.6895, 139.6917));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MailService mailService = getMailService();

        List<ContactPerson> contactList = ContactPerson.findAll();

        if (contactList.isEmpty()) {
            resp.sendRedirect("eventlist.jsp?email_sent=0&event_in_email=0");
            return;
        }

        int mailSent = 0;
        int eventsInMailSent = 0;

        contactList = ContactPerson.where(ContactPerson.LOCATION + "<>''");

        for (ContactPerson person : contactList) {
            Location location = locations.get(person.getLocation());

            List<Event> newEventList = Event.where("location in (" + getCloseByLocationStrings(location.getName())+")");
            eventsInMailSent += newEventList.size();

            if(!newEventList.isEmpty()) {
                mailSent = getMailSent(person, newEventList, mailSent);
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

    public String getCloseByLocationStrings(String locationName) {
        Location location = locations.get(locationName);
        String locationsString = "";

        if(location != null){
            for (Location loc : locations.values()) {
                if (loc.distanceFrom(location) <= CLOSE_BY_DISTANCE) {
                    locationsString += "\"" + loc.getName() + "\", ";
                }
            }
            locationsString = locationsString.substring(0, locationsString.length() - 2);
        }
        return locationsString;
    }
}

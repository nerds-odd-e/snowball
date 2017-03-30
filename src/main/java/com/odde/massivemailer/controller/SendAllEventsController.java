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
        locations.put("Jakarta", new Location("Jarkata", -6.174465, 106.822745));
        locations.put("Kuala Lumpur", new Location("Kuala Lumpur", 3.139003, 101.686855));
        locations.put("Seoul", new Location("Seoul", 37.566535, 126.977969));
        locations.put("New Delhi", new Location("New Delhi", 28.613939, 77.209021));
        locations.put("Bangalore", new Location("Bangalore", 12.971599, 77.594563));
        locations.put("Hanoi", new Location("Hanoi", 21.027764, 105.834160));
        locations.put("Manila", new Location("Manila", 14.599512, 120.984219));
        locations.put("Beijing", new Location("Beijing", 39.904211, 116.407395));
        locations.put("Shanghai", new Location("Shanghai", 31.230416, 121.473701));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MailService mailService = getMailService();

        List<ContactPerson> contactList = ContactPerson.findAll();

        if (contactList.isEmpty()) {
            resp.sendRedirect("eventlist.jsp?email_sent=0&event_in_email=0");
            return;
        }

        int totalMailsSent = 0;
        int totalEventsSent = 0;

        contactList = ContactPerson.where(ContactPerson.LOCATION + "<>''");

        for (ContactPerson person : contactList) {
            Location location = locations.get(person.getLocation());

            List<Event> eventsNearContact = Event.where("location in (" + getCloseByLocationStrings(location.getName())+")");
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

package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.service.EventService;
import com.odde.massivemailer.service.NotificationService;
import com.odde.massivemailer.service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SendAllEventsController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GMailService mailService = createGmailService();
        NotificationService notificationService = new NotificationServiceSqlite();

        List<Event> eventList = Event.findAll();

        SqliteContact contactService = new SqliteContact();
        List<ContactPerson> contactList = contactService.getContactList();

        if (eventList.isEmpty() || contactList.isEmpty()) {
            resp.sendRedirect("eventlist.jsp?email_sent=N/A&event_in_email=N/A");
            return;
        }

        Mail mail = createMailWithEvents(eventList);

        int mailSent = 0;
        for (ContactPerson person : contactList) {
            mail.setReceipts(Collections.singletonList(person.getEmail()));

            try {
                Notification notification = notificationService.save(mail.asNotification());
                mail.setNotification(notification);

                mailService.send(mail);

                ++mailSent;
            } catch (EmailException e) {
                throw new IOException(e);
            }
        }

        String redirectUrl = String.format("eventlist.jsp?email_sent=%s&event_in_email=%s",
                mailSent,
                eventList.size()
        );

        resp.sendRedirect(redirectUrl);
    }

    private Mail createMailWithEvents(List<Event> eventList) {
        String content = eventList.stream()
                .map(e -> e.getTitle())
                .collect(Collectors.joining("<br/>\n"));

        Mail mail = new Mail();
        mail.setSubject("Event Invitation");
        mail.setContent(content);
        mail.setMessageId(System.currentTimeMillis());

        return mail;
    }
}

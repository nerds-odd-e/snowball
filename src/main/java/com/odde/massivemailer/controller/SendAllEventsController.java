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

public class SendAllEventsController extends HttpServlet {

    private static final String SMTP_ADDR = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String EMAIL_USERID = "MM_EMAIL_USERID";
    private static final String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";

    private GMailService mailService = null;

    private NotificationService notificationService;

    public SendAllEventsController() {
        mailService = new GMailService(getSmtpConfiguration());
        notificationService = new NotificationServiceSqlite();

    }

    public void setMailService(GMailService mailService) {
        this.mailService = mailService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EventService eventService = new EventServiceImpl();

        List<Event> eventList = eventService.getAll();

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

                SMTPConfiguration config = getSmtpConfiguration();
                GMailService mailService = new GMailService(config);
                mailService.send(mail);

                ++mailSent;
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }

        String redirectUrl = String.format("eventlist.jsp?email_sent=%s&event_in_email=%s",
                mailSent,
                eventList.size()
        );

        resp.sendRedirect(redirectUrl);
    }

    protected Mail createMailWithEvents(List<Event> eventList) {
        String content = eventList.stream()
                .map(e -> e.getTitle())
                .collect(Collectors.joining("\n"));

        Mail mail = new Mail();
        mail.setSubject("Event Invitation");
        mail.setContent(content);
        mail.setMessageId(System.currentTimeMillis());

        return mail;
    }

    private SMTPConfiguration getSmtpConfiguration() {
        return new SMTPConfiguration(
                System.getenv(EMAIL_USERID),
                System.getenv(EMAIL_PASSWORD),
                SMTP_ADDR, PORT);
    }
}

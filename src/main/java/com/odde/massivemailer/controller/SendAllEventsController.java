package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.EventService;
import com.odde.massivemailer.service.impl.EventServiceImpl;
import com.odde.massivemailer.service.impl.SqliteContact;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SendAllEventsController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EventServiceImpl eventService = new EventServiceImpl();
        List<Event> eventList = eventService.getAll();

        SqliteContact contactService = new SqliteContact();
        List<ContactPerson> contactList = contactService.getContactList();

        if (eventList.isEmpty() || contactList.isEmpty())
        {
            resp.sendRedirect("eventlist.jsp?email_sent=N/A&event_in_email=N/A");
            return;
        }

        String redirectUrl = String.format("eventlist.jsp?email_sent=%s&event_in_email=%s",
                contactList.size(),
                eventList.size()
        );

        resp.sendRedirect(redirectUrl);
    }

}

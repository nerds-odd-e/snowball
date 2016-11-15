package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.impl.SqliteContact;
import com.odde.massivemailer.service.impl.SqliteEmail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EmailHistoryControllerTest {
    EmailHistoryController emailHistoryController;
    EmailService emailService;
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    List<Mail> sentEmails = new ArrayList<>();

    //@Before
    public void setUpMockService() {
        emailService = mock(EmailService.class);
        emailHistoryController = new EmailHistoryController(emailService);
        Mail mail1 = new Mail();
        mail1.setKey("1");
        mail1.setSubject("Promotional test");
        mail1.setSentDate(null);
        sentEmails.add(mail1);

    }

    //@Test
    public void returnContactsInJSON() throws Exception {
        emailService.setSentEmailList(sentEmails);
        when(emailService.getSentEmailList()).thenReturn(sentEmails);
        emailHistoryController.doGet(req, res);
        assertEquals(new Gson().toJson(sentEmails),res.getContentAsString());
    }

}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.factory.SentMailFactory;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.serialiser.AppGson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


@RunWith(TestWithDB.class)
public class EmailOpenedCounterControllerTest {
    private final EmailOpenedCounterController emailOpenedCounterController = new EmailOpenedCounterController();
    private final SentMailFactory sentMailFactory = new SentMailFactory();
    private final MockHttpServletRequest req = new MockHttpServletRequest();
    private final MockHttpServletResponse res = new MockHttpServletResponse();

    @Test
    public void returnEmailSubject() throws Exception {

        SentMail mail = sentMailFactory.buildSentMailWithSubject("Promotional test");
        req.setParameter("id", String.valueOf(mail.getStringId()));

        emailOpenedCounterController.doGet(req, res);

        //assertEquals("ss", res.getContentAsString());
        SentMail result = AppGson.getGson().fromJson(res.getContentAsString(), SentMail.class);

        assertEquals("Promotional test", result.getSubject());
        assertEquals(mail.getId(), result.getId());
    }

    @Test
    public void returnWarningMessage() throws IOException {
        emailOpenedCounterController.doGet(req, res);
        assertEquals("{'error': 'null id'}", res.getContentAsString());
    }
}


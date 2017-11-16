package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.SentMailDataMother;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.serialiser.AppGson;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


@RunWith(TestWithDB.class)
public class EmailOpenedCounterControllerTest {
    EmailOpenedCounterController emailOpenedCounterController = new EmailOpenedCounterController();
    SentMailDataMother sentMailDataMother = new SentMailDataMother();
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();

    @Test
    public void returnEmailSubject() throws Exception {

        SentMail mail = sentMailDataMother.buildSentMailWithSubject("Promotional test");
        req.setParameter("id", String.valueOf(mail.getLongId()));

        emailOpenedCounterController.doGet(req, res);

        SentMail result = AppGson.getGson().fromJson(res.getContentAsString(), SentMail.class);

        assertEquals("Promotional test", result.getSubject());
        assertEquals(mail.getId(), Long.parseLong(result.getId().toString()));
    }

    @Test
    public void returnWarningMessage() throws ServletException, IOException {
        emailOpenedCounterController.doGet(req, res);
        assertEquals("{'error': 'null id'}", res.getContentAsString());
    }

}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.factory.SentMailFactory;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.serialiser.AppGson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class EmailHistoryControllerTest {
    private EmailHistoryController emailHistoryController;
    private final SentMailFactory sentMailFactory = new SentMailFactory();
    private final MockHttpServletRequest req = new MockHttpServletRequest();
    private final MockHttpServletResponse res = new MockHttpServletResponse();

    @Before
    public void setUpMockService() {
        emailHistoryController = new EmailHistoryController();
        sentMailFactory.buildSentMailWithSubject("Promotional test");
    }

    @Test
    public void returnContactsInJSON() throws Exception {
        emailHistoryController.doGet(req, res);

        SentMail[] result = AppGson.getGson().fromJson(res.getContentAsString(), SentMail[].class);
        assertEquals("Promotional test", result[0].getSubject());
    }
}
package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.SentMailDataMother;
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
    EmailHistoryController emailHistoryController;
    SentMailDataMother sentMailDataMother = new SentMailDataMother();
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();

    @Before
    public void setUpMockService() {
        emailHistoryController = new EmailHistoryController();
        sentMailDataMother.buildSentMailWithSubject("Promotional test");
    }

    @Test
    public void returnContactsInJSON() throws Exception {
        emailHistoryController.doGet(req, res);

        SentMail[] result = AppGson.getGson().fromJson(res.getContentAsString(), SentMail[].class);
        assertEquals("Promotional test", result[0].getSubject());
    }
}
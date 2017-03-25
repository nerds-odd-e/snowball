package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Notification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class EmailHistoryControllerTest {
    EmailHistoryController emailHistoryController;
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();

    @Before
    public void setUpMockService() {
        emailHistoryController = new EmailHistoryController();
        Notification mail1 = new Notification();
        mail1.setSubject("Promotional test");
        mail1.setSentDate(null);
        mail1.saveIt();

    }

    @Test
    public void returnContactsInJSON() throws Exception {
        emailHistoryController.doGet(req, res);
        assertEquals("[{\"id\":\"1\",\"notification_id\":\"0\",\"subject\":\"Promotional test\"}]",res.getContentAsString());
    }

}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Notification;
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
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    int email_id = 2;

    @Test
    public void returnEmailSubject() throws Exception {
        Notification notification = Notification.createIt("subject", "xxx");
        req.setParameter("id", String.valueOf(notification.getLongId()));
        emailOpenedCounterController.doGet(req, res);
        assertEquals("{\"subject\":\"xxx\", \"sent_at\":\"null\", \"total_open_count\":0, \"emails\":[]}", res.getContentAsString());
    }

    @Test
    public void returnWarningMessage() throws ServletException, IOException {
        emailOpenedCounterController.doGet(req, res);
        assertEquals("{'error': 'null id'}", res.getContentAsString());
    }

}

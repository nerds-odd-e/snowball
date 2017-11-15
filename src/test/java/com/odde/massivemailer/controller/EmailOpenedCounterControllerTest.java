package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.Template;
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
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    int email_id = 2;

    @Test
    @Ignore
    public void returnEmailSubject() throws Exception {
        Template template = Template.createIt("TemplateName", "Template");
        SentMail mail = SentMail.createIt("template_id", template.getId(), "subject", "Promotional test");
        req.setParameter("id", String.valueOf(mail.getLongId()));
        emailOpenedCounterController.doGet(req, res);
        String json = res.getContentAsString();
        SentMail result = AppGson.getGson().fromJson(json, SentMail.class);
        assertEquals("Promotional test", result.getSubject());
    }

    @Test
    public void returnWarningMessage() throws ServletException, IOException {
        emailOpenedCounterController.doGet(req, res);
        assertEquals("{'error': 'null id'}", res.getContentAsString());
    }

}

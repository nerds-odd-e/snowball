package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class EmailOpenedCounterControllerTest {
    EmailOpenedCounterController emailOpenedCounterController;
    EmailServiceForTest emailService;
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    int email_id = 2;
    @Before
    public void setUpMockService() {
        emailService = new EmailServiceForTest();
        emailOpenedCounterController = new EmailOpenedCounterController(emailService);
    }

    @Test
    public void returnEmailSubject() throws Exception {
        req.setAttribute("id", email_id);
        emailOpenedCounterController.doGet(req, res);
        assertEquals(emailService.getEmailCounterJson(email_id), res.getContentAsString());
    }

    @Test
    public void returnWarningMessage() throws ServletException, IOException {
        emailOpenedCounterController.doGet(req, res);
        assertEquals("{'error': 'null id'}", res.getContentAsString());
    }

    private class EmailServiceForTest implements EmailService {
        @Override
        public List<Mail> getSentEmailList() {
            return null;
        }

        @Override
        public void setSentEmailList(List<Mail> emailList) {

        }

        @Override
        public List<Mail> getOpenedEmailCountList() {
            return null;
        }

        @Override
        public void destroyAll() {

        }

        @Override
        public void setOpenedEmailCountList(int i) {

        }

        @Override
        public String getEmailCounterJson(int email_id) {
            return "fake jason of" + String.valueOf(email_id);
        }

        @Override
        public void addEmail(int i, String subject) {

        }

        @Override
        public void increaseCounterOfEmailByOne(int i, String s) {

        }

    }
}

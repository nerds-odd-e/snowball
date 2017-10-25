package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.MailLog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;

@RunWith(TestWithDB.class)
public class ReportControllerTest {
    private ReportController controller = new ReportController();
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    public void shouldGetNoReports() throws ServletException, IOException {
        controller.doGet(request, response);
        Assert.assertEquals("[]", response.getContentAsString());
    }
    @Test
    public void shouldGetOneReport() throws ServletException, IOException {
        Date date = new Date();
        MailLog mailLog = new MailLog();
        mailLog.set("contact_person_id", 1);
        mailLog.set("course_id", 1);
        mailLog.set("sent_at", date);
        mailLog.saveIt();
        MailLog mailLog2 = new MailLog();
        mailLog2.set("contact_person_id", 1);
        mailLog2.set("course_id", 2);
        mailLog2.set("sent_at", date);
        mailLog2.saveIt();
        new ContactPerson("", "darai0512@odd-e.com", "", "", "Tokyo, Japan").saveIt();
        controller.doGet(request, response);
        Assert.assertEquals("[{\"course_count\":2,\"email\":\"darai0512@odd-e.com\",\"location\":\"Tokyo, Japan\",\"sent_at\":" + date.getTime() + "}]", response.getContentAsString());
    }
    @Test
    public void shouldGetTwoUserReports() throws ServletException, IOException {
        Date date = new Date();
        MailLog mailLog = new MailLog();
        mailLog.set("contact_person_id", 1);
        mailLog.set("course_id", 1);
        mailLog.set("sent_at", date);
        mailLog.saveIt();
        MailLog mailLog2 = new MailLog();
        mailLog2.set("contact_person_id", 2);
        mailLog2.set("course_id", 1);
        mailLog2.set("sent_at", date);
        mailLog2.saveIt();
        new ContactPerson("", "darai0512@odd-e.com", "", "", "Tokyo, Japan").saveIt();
        new ContactPerson("", "yhsampei@odd-e.com", "", "", "Tokyo, Japan").saveIt();
        controller.doGet(request, response);
        Assert.assertEquals(
            "[{\"course_count\":1,\"email\":\"darai0512@odd-e.com\",\"location\":\"Tokyo, Japan\",\"sent_at\":"
            + date.getTime()
            + "},{\"course_count\":1,\"email\":\"yhsampei@odd-e.com\",\"location\":\"Tokyo, Japan\",\"sent_at\":" +
            + date.getTime()
            + "}]"
        , response.getContentAsString());
    }
}

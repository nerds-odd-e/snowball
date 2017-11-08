package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.CourseContactNotification;
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
        CourseContactNotification courseContactNotification = new CourseContactNotification();
        courseContactNotification.set("contact_person_id", 1);
        courseContactNotification.set("course_id", 1);
        courseContactNotification.set("sent_at", date);
        courseContactNotification.saveIt();
        CourseContactNotification courseContactNotification2 = new CourseContactNotification();
        courseContactNotification2.set("contact_person_id", 1);
        courseContactNotification2.set("course_id", 2);
        courseContactNotification2.set("sent_at", date);
        courseContactNotification2.saveIt();
        new ContactPerson("", "darai0512@odd-e.com", "", "", "Tokyo, Japan").saveIt();
        controller.doGet(request, response);
        Assert.assertEquals("[{\"course_count\":2,\"email\":\"darai0512@odd-e.com\",\"location\":\"Tokyo, Japan\",\"sent_at\":" + date.getTime() + "}]", response.getContentAsString());
    }
    @Test
    public void shouldGetTwoUserReports() throws ServletException, IOException {
        Date date = new Date();
        CourseContactNotification courseContactNotification = new CourseContactNotification();
        courseContactNotification.set("contact_person_id", 1);
        courseContactNotification.set("course_id", 1);
        courseContactNotification.set("sent_at", date);
        courseContactNotification.saveIt();
        CourseContactNotification courseContactNotification2 = new CourseContactNotification();
        courseContactNotification2.set("contact_person_id", 2);
        courseContactNotification2.set("course_id", 1);
        courseContactNotification2.set("sent_at", date);
        courseContactNotification2.saveIt();
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

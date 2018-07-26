package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class CourseDetailControllerTest {

    private CourseDetailController controller = new CourseDetailController();
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();


    @Test
    public void doGet_containsTitle() throws IOException {
        new Course("CSD Tokyo", "hoge", "Tokyo").saveIt();
        String id = Course.getCourseByName("CSD Tokyo").getId().toString();
        request.setParameter("id", id);
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"courseName\":\"CSD Tokyo\""));

    }

    @Test
    public void doGet_containsCourseParticipants() throws IOException {
        new Course("CSD Tokyo", "hoge", "Tokyo").saveIt();
        Integer courseId = Integer.valueOf(Course.getCourseByName("CSD Tokyo").getId().toString());
        new ContactPerson("Tommy", "tommy@example.com", "Smith", "BIZ", "Tokyo").save();
        Integer participantId = Integer.valueOf(ContactPerson.getContactByEmail("tommy@example.com").getId().toString());
        new Participant(participantId, courseId).save();

        request.setParameter("id", courseId.toString());
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"participants\":["));
        assertThat(response.getContentAsString(), containsString("\"email\":\"tommy@example.com\""));
    }
}
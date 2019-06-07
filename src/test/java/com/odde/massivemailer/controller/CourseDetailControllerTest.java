package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static com.odde.massivemailer.factory.ContactFactory.uniqueContact;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class CourseDetailControllerTest {

    private final CourseDetailController controller = new CourseDetailController();
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();


    @Test
    public void doGet_containsTitle() throws IOException {
        Course.repository().createIt("courseName", "CSD Tokyo", "courseDetails", "hoge");
        String id = Course.getCourseByName("CSD Tokyo").getStringId();
        request.setParameter("id", id);
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"courseName\":\"CSD Tokyo\""));

    }

    @Test
    public void doGet_containsCourseParticipants() throws IOException {
        Course.repository().createIt("courseName", "CSD Tokyo", "courseDetails", "hoge");
        ObjectId courseId = Course.getCourseByName("CSD Tokyo").getId();
        ContactPerson contactPerson = uniqueContact();
        contactPerson.setFirstName("Tommy");
        contactPerson.setEmail("tommy@example.com");
        contactPerson.saveIt();
        ObjectId participantId = ContactPerson.getContactByEmail("tommy@example.com").getId();
        new Participant(participantId, courseId).saveIt();

        request.setParameter("id", courseId.toString());
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"participants\":["));
        assertThat(response.getContentAsString(), containsString("\"email\":\"tommy@example.com\""));
        assertThat(response.getContentAsString(), containsString("\"name\":\"Tommy\""));
    }
}
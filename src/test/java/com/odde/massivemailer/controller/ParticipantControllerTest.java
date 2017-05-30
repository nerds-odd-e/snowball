package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by csd on 30/5/17.
 */
@RunWith(TestWithDB.class)
public class ParticipantControllerTest {


    private ContactsController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;


    @Before
    public void setUpMockService() {
        controller = new ContactsController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void returnParticipantsInCourse() throws Exception {
        ContactPerson person = new ContactPerson("John", "john@gmail.com", "Doe", "ComA");
        person.save();

        Course newCourse = new Course.CourseBuilder().setCoursename("CSD_NEW").setAddress("roberts lane").setLocation("SG").setCoursedetails("CSD new course details").setDuration("5d").setInstructor(" roof ").setStartdate(new Date()).build();
        newCourse.save();

        ContactPerson person1 = ContactPerson.getContactByEmail("john@gmail.com");
        Course csdCourse = Course.getCourseByName("CSD_NEW");

        Participant participant = new Participant();
        participant.setCourseId(String.valueOf(csdCourse.getId()));
        participant.setContactPersonId(String.valueOf(person1.getId()));
        participant.saveIt();

        request.setParameter("courseId", String.valueOf(csdCourse.getId()));
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"email\":\"john@gmail.com\""));
    }
}

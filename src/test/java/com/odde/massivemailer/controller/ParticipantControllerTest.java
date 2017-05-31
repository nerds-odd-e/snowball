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
        ContactPerson newPerson = new ContactPerson("John", "john@gmail.com", "Doe", "ComA");
        newPerson.save();

        Course newCourse = new Course.CourseBuilder().setCoursename("CSD_NEW").setAddress("roberts lane").setLocation("SG").setCoursedetails("CSD new course details").setDuration("5d").setInstructor(" roof ").setStartdate(new Date()).build();
        newCourse.save();

        ContactPerson savedPerson = ContactPerson.getContactByEmail("john@gmail.com");
        Course savedCourse = Course.getCourseByName("CSD_NEW");

        Assert.assertNotNull(savedPerson);
        Assert.assertNotNull(savedPerson.getId());
        Assert.assertNotNull(savedCourse);
        Assert.assertNotNull(savedCourse.getId());
        System.out.println(savedPerson.getId());
        System.out.println(savedCourse.getId());

        new Participant(1, 40).save();

        request.setParameter("courseId", String.valueOf(savedCourse.getId()));
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"email\":\"john@gmail.com\""));
    }
}

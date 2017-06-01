package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.CourseDataMother;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class  ParticipantControllerTest {

    private ParticipantController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    CourseDataMother dataMother = new CourseDataMother();



    @Before
    public void setUpMockService() {
        controller = new ParticipantController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void returnParticipantsInCourse() throws Exception {
        ContactPerson newPerson = new ContactPerson("John", "john@gmail.com", "Doe", "ComA");
        newPerson.save();

        Course newCourse = dataMother.build_csd_course();
        newCourse.save();

        ContactPerson savedPerson = ContactPerson.getContactByEmail("john@gmail.com");
        Course savedCourse = Course.getCourseByName("CSD_NEW");

        Assert.assertNotNull(savedPerson);
        Assert.assertNotNull(savedPerson.getId());
        Assert.assertNotNull(savedCourse);
        Assert.assertNotNull(savedCourse.getId());

        new Participant((Integer)savedPerson.getId(), (Integer)savedCourse.getId()).save();

        request.setParameter("courseId", String.valueOf(savedCourse.getId()));
        controller.doGet(request, response);
        //System.out.println(response.getContentAsString());
        assertThat(response.getContentAsString(), containsString("\"email\":\"john@gmail.com\""));
    }

}

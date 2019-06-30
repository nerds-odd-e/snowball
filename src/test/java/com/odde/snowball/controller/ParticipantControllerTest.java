package com.odde.snowball.controller;

import com.odde.TestWithDB;
import com.odde.snowball.factory.CourseFactory;
import com.odde.snowball.model.ContactPerson;
import com.odde.snowball.model.Course;
import com.odde.snowball.model.Participant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.odde.snowball.factory.ContactFactory.uniqueContact;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class  ParticipantControllerTest {

    private ParticipantController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private final CourseFactory dataMother = new CourseFactory();



    @Before
    public void setUpMockService() {
        controller = new ParticipantController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void returnParticipantsInCourse() throws Exception {
        ContactPerson newPerson = uniqueContact();
        newPerson.save();

        Course newCourse = dataMother.build_csd_course();
        newCourse.save();

        ContactPerson savedPerson = ContactPerson.getContactByEmail(newPerson.getEmail());
        Course savedCourse = Course.getCourseByName("CSD_NEW");

        Assert.assertNotNull(savedPerson);
        Assert.assertNotNull(savedPerson.getId());
        Assert.assertNotNull(savedCourse);
        Assert.assertNotNull(savedCourse.getId());

        new Participant(savedPerson.getId(), savedCourse.getId()).save();

        request.setParameter("courseId", String.valueOf(savedCourse.getId()));
        controller.doGet(request, response);
        assertThat(response.getContentAsString(), containsString("\"email\":\""+newPerson.getEmail()+"\""));
    }

}

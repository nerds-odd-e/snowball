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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class  ParticipantControllerTest {


    private ParticipantController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;


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

        Map<String, String> params = new HashMap<>();

        params.put("coursename", "CSD_NEW");
        params.put("location", "SG");
        params.put("address", "roberts lane");
        params.put("coursedetails", "CSD new course details");
        params.put("duration", "5");
        params.put("instructor", "ROOF");
        params.put("startdate", "2017-05-15");

        Course newCourse = new Course(params);
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

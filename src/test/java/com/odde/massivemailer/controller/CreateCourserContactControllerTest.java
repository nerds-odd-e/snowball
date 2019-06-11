package com.odde.snowball.controller;

import com.odde.TestWithDB;
import com.odde.snowball.factory.CourseFactory;
import com.odde.snowball.model.ContactPerson;
import com.odde.snowball.model.Course;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class CreateCourserContactControllerTest  {
    private CreateCourseContactController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private final CourseFactory dataMother = new CourseFactory();
    private final Course course = dataMother.csd_course();

    @Before
    public void setUpMockService() {
        controller = new CreateCourseContactController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void shouldNotAddNonexistingParticipant() throws Exception {
        request.setParameter("courseId", course.getStringId());
        request.setParameter("participantEmail", new String[]{ "doesn't_exist"});
        controller.doPost(request,response);
        assertEquals("registerParticipant.jsp?status=fail&msg=Unable to register participants", response.getRedirectedUrl());
    }

    @Test
    public void shouldHaveSucessfulMessageWhenCreated() throws Exception {
        ContactPerson participant = dataMother.contact_alex();
        request.setParameter("courseId", course.getStringId());
        request.setParameter("participantEmail", participant.getEmail());

        controller.doPost(request,response);

        List<ContactPerson> participants = course.participants();
        assertEquals(participant.getStringId(), participants.get(participants.size() - 1).getStringId());
    }

}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.factory.CourseFactory;
import com.odde.massivemailer.model.Participant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class ParticipantControllerTest_Temp {

    private ParticipantController_Temp controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    CourseFactory dataMother = new CourseFactory();

    @Before
    public void setUpMockService() {
        controller = new ParticipantController_Temp();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void saveParticipantsInCourse() throws Throwable {
        request.setParameter("courseId", "123");
        request.setParameter("participants", "tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore");
        List<Participant> participants = Participant.whereHasCourseId("123");
        controller.doPost(request, response);

        assertEquals("enrollParticipant.jsp?courseId=123", response.getRedirectedUrl());
        assertEquals(1, participants.size());
    }
}

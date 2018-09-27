package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.factory.CourseFactory;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Question2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(TestWithDB.class)
public class QuestionCreationControllerTest {
    private QuestionCreationController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockServicCreateCourserContactControllerTeste() {
        controller = new QuestionCreationController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void shouldNotAddNonexistingParticipant() throws Exception {
        request.setParameter("body", "body3");
        request.setParameter("advice", "advice");
        controller.doPost(request,response);

        assertTrue(Question2.find("body = 'body3' AND advice = 'advice'").size() > 0);
    }
}

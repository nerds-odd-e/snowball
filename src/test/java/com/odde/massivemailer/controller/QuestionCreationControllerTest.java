package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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
    public void shouldNotAddNonExistingParticipant() throws Exception {
        request.setParameter("category","Organization");
        request.setParameter("body", "body3");
        request.setParameter("advice", "advice");
        request.setParameter("answer_1", "answer_1");
        request.setParameter("answer_2", "answer_2");
        controller.doPost(request,response);

        assertTrue(Question.find("body = 'body3' AND advice = 'advice' AND category = 'Organization'").size() > 0);
    }
}

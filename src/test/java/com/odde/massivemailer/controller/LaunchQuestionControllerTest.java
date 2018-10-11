package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.Quiz;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class LaunchQuestionControllerTest {

    private LaunchQuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new LaunchQuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("answeredCount", 0);
        mockQuestion(5);

    }

    @Test
    public void redirect_to_question_jsp()
            throws Exception {
        controller.doGet(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void mustGetQuestionId() throws Exception {
        controller.doGet(request, response);
        Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
        Integer correctlyAnsweredQuestions = (Integer) request.getSession().getAttribute("correctlyAnsweredCount");

        assertNotNull(quiz);
        assertNotNull(correctlyAnsweredQuestions);
        assertTrue(correctlyAnsweredQuestions.equals(0));
    }

    private void mockQuestion(int numberOfQuestion) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index));
    }

}



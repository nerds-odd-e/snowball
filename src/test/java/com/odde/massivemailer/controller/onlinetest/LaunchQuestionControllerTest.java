package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.onlinetest.Quiz;
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


    }

    @Test
    public void redirect_to_question_jsp()
            throws Exception {
        mockQuestion();
        controller.doGet(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void mustGetQuestionId() throws Exception {
        mockQuestion();
        controller.doGet(request, response);
        Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
        Integer correctlyAnsweredQuestions = (Integer) request.getSession().getAttribute("correctlyAnsweredCount");

        assertNotNull(quiz);
        assertNotNull(correctlyAnsweredQuestions);
        assertEquals(0, (int) correctlyAnsweredQuestions);
    }

    @Test
    public void redirectToAddQuestionPageIfNoQuestionsInQuiz() throws Exception {
        controller.doGet(request,response);
        assertEquals("add_question.jsp", response.getRedirectedUrl());
    }

    private void mockQuestion() {
        IntStream.range(0, 5).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index));
    }

}



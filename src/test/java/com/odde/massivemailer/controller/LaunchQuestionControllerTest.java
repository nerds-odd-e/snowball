package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

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
        controller.doGet(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void mustSetCorrectlyAnsweredQuestionsToZero()
            throws Exception {
        controller.doGet(request, response);
        int correctlyAnsweredCount = (int) request.getSession().getAttribute("correctlyAnsweredCount");
        assertEquals(0, correctlyAnsweredCount);
    }


    @Test
    public void mustGetQuesrtionId()
            throws Exception {
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        controller.doGet(request, response);
        Stream<Long> questsion_ids = (Stream<Long>) request.getSession().getAttribute("questionIds");
        assertEquals(question.getLongId(), questsion_ids.findFirst().get());
    }


}



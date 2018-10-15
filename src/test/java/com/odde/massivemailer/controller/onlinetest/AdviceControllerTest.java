package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Quiz;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(TestWithDB.class)
public class AdviceControllerTest {

    private static final int CORRECTLY_ANSWERED_QUESTIONS = 3;
    private AdviceController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setup(){
        controller = new AdviceController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        request.getSession().setAttribute("quiz", new Quiz(5));
        request.getSession().setAttribute("correctlyAnsweredCount", CORRECTLY_ANSWERED_QUESTIONS);
    }

    @Test
    public void shouldNotRemoveQuizFromSessionAttributes() throws IOException {
        controller.doPost(request, response);
        assertNotNull(request.getSession().getAttribute("quiz"));
    }

    @Test
    public void shouldNotChangeCorectlyAnsweredCountFromSessionAttributes() throws IOException {
        controller.doPost(request,response);
        int correctlyAnsweredCount = (Integer)request.getSession().getAttribute("correctlyAnsweredCount");
        assertEquals(CORRECTLY_ANSWERED_QUESTIONS, correctlyAnsweredCount);
    }

    @Test
    public void checkFromAdvicePageToQuestionPage() throws Exception {
        Quiz quiz = mock(Quiz.class);
        when(quiz.hasNextQuestion()).thenReturn(true);
        request.getSession().setAttribute("quiz", quiz);

        controller.doPost(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void checkFromAdvicePageToEndOfTestPageWhenNoMoreQuestions() throws Exception {
        Quiz quiz = mock(Quiz.class);
        when(quiz.hasNextQuestion()).thenReturn(false);
        request.getSession().setAttribute("quiz", quiz);

        controller.doPost(request, response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

}
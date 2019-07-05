package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.enumeration.TestType;
import com.odde.snowball.model.onlinetest.OnlinePractice;
import com.odde.snowball.model.onlinetest.OnlineQuiz;
import com.odde.snowball.model.onlinetest.OnlineTest;
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
    public void setup() {
        controller = new AdviceController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("onlineTest", OnlineQuiz.createOnlineQuiz(5));
    }

    @Test
    public void shouldNotRemoveOnlineTestFromSessionAttributes() throws IOException {
        controller.doPost(request, response);
        assertNotNull(request.getSession().getAttribute("onlineTest"));
    }

    @Test
    public void checkFromAdvicePageToQuestionPage() throws Exception {
        OnlineQuiz onlineTest = mock(OnlineQuiz.class);
        String redirectUrl = "/onlinetest/question.jsp";

        when(onlineTest.getNextPageName()).thenReturn(redirectUrl);
        when(onlineTest.hasNextQuestion()).thenReturn(true);
        when(onlineTest.getTestType()).thenReturn(TestType.OnlineTest);
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        assertEquals(redirectUrl, response.getRedirectedUrl());
    }

    @Test
    public void checkFromAdvicePageToEndOfTestPageWhenNoMoreQuestions() throws Exception {
        OnlineQuiz onlineTest = mock(OnlineQuiz.class);
        String redirectUrl = "/onlinetest/end_of_test.jsp";

        when(onlineTest.getNextPageName()).thenReturn(redirectUrl);
        when(onlineTest.hasNextQuestion()).thenReturn(false);
        when(onlineTest.getTestType()).thenReturn(TestType.OnlineTest);
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        assertEquals(redirectUrl, response.getRedirectedUrl());
    }

    @Test
    public void should_redirect_to_end_of_practice_page_if_type_is_practice() throws IOException {
        OnlinePractice onlineTest = mock(OnlinePractice.class);
        String redirectUrl = "/practice/completed_practice.jsp";

        when(onlineTest.getNextPageName()).thenReturn(redirectUrl);
        when(onlineTest.hasNextQuestion()).thenReturn(false);
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        assertEquals(redirectUrl, response.getRedirectedUrl());
    }
}
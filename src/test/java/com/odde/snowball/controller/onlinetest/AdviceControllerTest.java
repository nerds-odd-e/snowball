package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
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
    public void setup(){
        controller = new AdviceController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        request.getSession().setAttribute("onlineTest", new OnlineTest(5));
    }

    @Test
    public void shouldNotRemoveOnlineTestFromSessionAttributes() throws IOException {
        controller.doPost(request, response);
        assertNotNull(request.getSession().getAttribute("onlineTest"));
    }

    @Test
    public void checkFromAdvicePageToQuestionPage() throws Exception {
        OnlineTest onlineTest = mock(OnlineTest.class);
        when(onlineTest.hasNextQuestion()).thenReturn(true);
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        assertEquals("/onlinetest/question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void checkFromAdvicePageToEndOfTestPageWhenNoMoreQuestions() throws Exception {
        OnlineTest onlineTest = mock(OnlineTest.class);
        when(onlineTest.hasNextQuestion()).thenReturn(false);
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        assertEquals("/onlinetest/end_of_test.jsp", response.getRedirectedUrl());
    }

}
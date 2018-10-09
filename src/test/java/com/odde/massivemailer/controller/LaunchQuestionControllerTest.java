package com.odde.massivemailer.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

class LaunchQuestionControllerTest {

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
    public void is_re_direct_to_question_jsp() throws Exception {
        controller.doGet(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

}
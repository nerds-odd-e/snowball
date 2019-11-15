package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class LaunchRetrainingControllerTest {

    private LaunchRetrainingController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new LaunchRetrainingController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void redirect_to_question() throws Exception
    {
        controller.doGet(request, response);
        assertEquals("/onlinetest/retraining.jsp", response.getRedirectedUrl());
    }
}
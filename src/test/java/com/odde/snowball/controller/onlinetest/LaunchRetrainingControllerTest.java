package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertEquals("/onlineTest/retraining", response.getRedirectedUrl());
    }
}
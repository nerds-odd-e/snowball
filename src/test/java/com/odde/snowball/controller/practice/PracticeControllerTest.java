package com.odde.snowball.controller.practice;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(TestWithDB.class)
public class PracticeControllerTest {

    private PracticeController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;


    @Before
    public void setUpMockService() {
        controller = new PracticeController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void retrievePracticeIfAlreadyExists() {

    }

    @Test
    public void createPracticeIfDoesNotExist() {

    }

}

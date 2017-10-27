package com.odde.massivemailer.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class CountryControllerTest {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private CountryController controller;

    @Before
    public void setUpMockService() {
        controller = new CountryController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void doGet() throws Exception {
        controller.doGet(request, response);
        assertThat(response.getContentAsString(), containsString("\"Netherlands Antilles\""));
    }
}
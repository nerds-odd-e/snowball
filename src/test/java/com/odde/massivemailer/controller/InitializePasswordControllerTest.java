package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class InitializePasswordControllerTest {
    private InitializePasswordController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    @Before
    public void setUpMockService() {
        controller = new InitializePasswordController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void initialPassword() throws Exception {
        request.setParameter("password", "abcd1234");
        request.setParameter("password_confirm", "abcd1234");

        controller.doPost(request, response);

        assertEquals("initialize-success.jsp", response.getRedirectedUrl());
    }
}

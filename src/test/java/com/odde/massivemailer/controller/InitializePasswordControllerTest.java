package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void showInitialPasswordViewSuccessfully() throws Exception {
        request.setParameter("token", "123123");
        controller.doGet(request,response);
        assertEquals("initialize_password.jsp?token=123123", response.getRedirectedUrl());
    }

    @Test
    public void showInitialPasswordViewWrongIfTokenNone() throws Exception {
        controller.doGet(request,response);
        assertTrue(response.getRedirectedUrl().contains("initialize_password_token_error.jsp"));
    }

    @Test
    public void initialPasswordSuccessfully() throws Exception {
        request.setParameter("token", "123123");
        request.setParameter("password","sdfgsdfgsdg");
        controller.doPost(request, response);
        assertEquals("initialize_password_success.jsp", response.getRedirectedUrl());
    }

    @Test
    public void initialPasswordWrong() throws Exception {
        request.setParameter("token", "123123");
        request.setParameter("password", "");
        controller.doPost(request, response);
        assertEquals("initialize_password.jsp", response.getRedirectedUrl());
    }

    @Test
    public void initialPasswordValidate() throws Exception {
        boolean isValidate = controller.validate("abcd1234");
        assertTrue(isValidate);
    }

}

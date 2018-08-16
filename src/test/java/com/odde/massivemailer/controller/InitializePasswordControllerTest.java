package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        User newUser = new User("user1@odd-e.com", "123123");
        newUser.saveIt();
        request.setParameter("token", "123123");
        controller.doGet(request,response);
        assertEquals("initialize_password.jsp?token=123123", response.getRedirectedUrl());
    }

    @Test
    public void showInitialPasswordViewFailWithDBNoExistsToken() throws ServletException, IOException {
        request.setParameter("token", "123123");
        controller.doGet(request,response);
        assertTrue(response.getRedirectedUrl().contains("initialize_password_token_error.jsp"));
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
        request.setParameter("password_confirm", "sdfgsdfgsdg");
        User newUser = new User("user1@odd-e.com", "123123");
        newUser.saveIt();
        controller.doPost(request, response);
        User user = User.findFirst("token = ? AND password IS NOT NULL", request.getParameter("token"));
        assertNotNull(user.get(User.PASSWORD));
        assertEquals("initialize_password_success.jsp", response.getRedirectedUrl());
    }

    @Test
    public void initialPasswordWrong() throws Exception {
        request.setParameter("token", "123123");
        request.setParameter("password", "");
        request.setParameter("password_confirm", "");
        controller.doPost(request, response);
        assertEquals("initialize_password.jsp", response.getRedirectedUrl());
    }

    @Test
    public void unmatchPasswordAndPasswordConfirm() throws Exception {
        request.setParameter("token", "123123");
        request.setParameter("password", "abcd123");
        request.setParameter("password_confirm", "123123");
        request.setParameter("email", "user1@odd-e.com");
        User newUser = new User("user1@odd-e.com", "123");
        newUser.saveIt();
        controller.doPost(request, response);
        assertEquals("initialize_password.jsp?error=unmatch", response.getRedirectedUrl());
    }

    @Ignore
    public void invalidTokenError() throws Exception {
        User user = new User("megumi@gmail.com", "123");
        user.saveIt();
        request.setParameter("token", "123123");
        request.setParameter("password","sdfgsdfgsdg");
        request.setParameter("password_confirm", "sdfgsdfgsdg");
        controller.doPost(request, response);

        assertTrue(response.getRedirectedUrl().contains("initialize_password_token_error.jsp"));
    }

    @Test
    public void initialPasswordValidate() throws Exception {
        boolean isValidate = controller.validate("abcd1234");
        assertTrue(isValidate);
    }
}

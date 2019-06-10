package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static com.odde.massivemailer.model.base.Repository.repo;
import static org.junit.Assert.*;

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
        User newUser = new User("user1@odd-e.com");
        newUser.save();
        request.setParameter("token", newUser.getToken());
        controller.doGet(request,response);
        assertEquals("initialize_password.jsp?token=" + newUser.getToken(), response.getRedirectedUrl());
    }

    @Test
    public void showInitialPasswordViewFailWithDBNoExistsToken() throws IOException {
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
        User newUser = new User("user1@odd-e.com");
        request.setParameter("token", newUser.getToken());
        request.setParameter("password","sdfgsdfgsdg");
        request.setParameter("password_confirm", "sdfgsdfgsdg");
        newUser.save();
        controller.doPost(request, response);
        User user = repo(User.class).findFirstBy("token", newUser.getToken());
        assertEquals("initialize_password_success.jsp", response.getRedirectedUrl());
        Assertions.assertThat(user.getHashdPassword()).isNotEmpty();
    }

    @Test
    public void initialPasswordWrong() throws Exception {
        request.setParameter("token", "123123");
        request.setParameter("password", "");
        request.setParameter("password_confirm", "");
        controller.doPost(request, response);
        assertEquals("initialize_password.jsp?error=error&token=123123", response.getRedirectedUrl());
    }

    @Test
    public void unmatchPasswordAndPasswordConfirm() throws Exception {
        request.setParameter("token", "123123");
        request.setParameter("password", "abcd123");
        request.setParameter("password_confirm", "123123");
        request.setParameter("email", "user1@odd-e.com");
        User newUser = new User("user1@odd-e.com");
        newUser.save();
        controller.doPost(request, response);
        assertEquals("initialize_password.jsp?error=error&token=123123", response.getRedirectedUrl());
    }

    @Test
    public void cannotSetPasswordWhenPasswordIsAlreadySet() throws Exception {
        User newUser = new User(request.getParameter("email"));
        request.setParameter("token", newUser.getToken());
        request.setParameter("password", "123123");
        request.setParameter("password_confirm", "123123");
        request.setParameter("email", "user1@odd-e.com");
        newUser.setPassword(request.getParameter("password"));
        newUser.save();

        controller.doPost(request, response);
        assertEquals("initialize_password.jsp?error=error", response.getRedirectedUrl());

    }

    @Test
    public void initialPasswordValidate() {
        boolean isValidate = User.validatePassword("abcd1234");
        assertTrue(isValidate);
    }
}

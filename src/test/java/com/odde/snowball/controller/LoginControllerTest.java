package com.odde.snowball.controller;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class LoginControllerTest {
    private LoginController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    @Before
    public void setUpMockService() {
        controller = new LoginController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void redirectLoginPageWhenIncorrectMailAndPassword() throws Exception {
        createUser();

        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "incorrectpass");

        controller.doPost(request, response);
        assertThat(response.getRedirectedUrl(), containsString("login.jsp?status=fail"));
    }

    @Test
    public void redirectCourseListPageWhenCorrectMailAndPassword() throws Exception {
        createUser();

        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "abcd1234");
        controller.doPost(request, response);

        assertEquals("/dashboard", response.getRedirectedUrl());
    }

    private User createUser() {
        User user = new User("mary@example.com");
        user.setupPassword("abcd1234");
        user.save();
        return user;
    }

    @Test
    public void setCookieWhenLoginSuccess() throws Exception {
        createUser();
        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "abcd1234");
        controller.doPost(request, response);

        Cookie cookie = response.getCookie("session_id");
        assertFalse(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
        assertNotNull(cookie);
        assertEquals("mary@example.com", cookie.getValue());
    }

    @Test
    public void setSessionWhenLoginSuccess() throws Exception {
        User user = createUser();
        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "abcd1234");
        controller.doPost(request, response);

        HttpSession session = request.getSession();
        assertNotNull(session);
        assertEquals(user.getId(), session.getAttribute("userId"));
    }

    @Test
    public void notSetCookieWhenLoginFail() throws Exception {
        createUser();
        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "incorrect");
        controller.doPost(request, response);

        Cookie cookie = response.getCookie("session_id");
        assertNull(cookie);
    }
}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

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
        User user = new User("mary@example.com");
        user.setPassword("abcd1234");
        user.saveIt();

        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "incorrectpass");

        controller.doPost(request, response);
        assertThat(response.getRedirectedUrl(), containsString("login.jsp"));
    }

    @Test
    public void redirectCourseListPageWhenCorrectMailAndPassword() throws Exception {
        User user = new User("mary@example.com");
        user.setPassword("abcd1234");
        user.saveIt();

        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "abcd1234");
        controller.doPost(request, response);

        assertThat(response.getRedirectedUrl(), containsString("course_list.jsp"));
    }
}

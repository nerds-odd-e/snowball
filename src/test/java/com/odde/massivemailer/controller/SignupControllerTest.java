package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class SignupControllerTest {

    private SignupController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new SignupController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void redirectTokkunPageFromSignup() throws Exception {
        request.setParameter("id", "");
        request.setParameter("email", "");
        request.setParameter("password", "");
        request.setParameter("password_confirm", "");

        controller.doPost(response);
        assertThat(response.getRedirectedUrl(), CoreMatchers.containsString("tokkun.jsp"));
    }

}
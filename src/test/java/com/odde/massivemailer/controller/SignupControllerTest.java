package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.odde.massivemailer.model.base.Repository.repo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @BeforeEach
    public void setupUserDB() {
//        User.deleteAll();
//        createUser();
    }

    @Test
    public void redirectToDashboardPageFromSignup() throws Exception {
        request.setParameter("userName", "yamada");
        request.setParameter("password", "passhoge");
        request.setParameter("password_confirm", "passhoge");
        controller.doPost(request, response);
        assertThat(response.getRedirectedUrl(), CoreMatchers.containsString("dashboard"));
    }

    @Test
    public void saveUserYamadaInfoToCookie() throws Exception {
        request.setParameter("userName", "yamada");
        request.setParameter("password", "passhoge");
        request.setParameter("password_confirm", "passhoge");

        controller.doPost(request, response);
        String actual = (String) request.getSession().getAttribute("userName");

        assertEquals("yamada", actual);
    }

    @Test
    public void saveUserTanakaInfoToCookie() throws Exception {
        request.setParameter("userName", "tanaka");
        request.setParameter("password", "passhoge");
        request.setParameter("password_confirm", "passhoge");

        controller.doPost(request, response);
        String actual = (String) request.getSession().getAttribute("userName");

        assertEquals("tanaka", actual);
    }

    @Test
    public void saveSessionIdTanakaInfoToCookie() throws Exception {
        request.setParameter("userName", "yamada");
        request.setParameter("email", "yamada@hoge.com");
        request.setParameter("password", "passhoge");
        request.setParameter("password_confirm", "passhoge");

        controller.doPost(request, response);

        String actual = response.getCookie("session_token").getValue();
        assertNotNull(actual);
        assertEquals(32, actual.length());
    }

    @Test
    public void registerTanakaDataIntoDatabase() throws Exception {
        request.setParameter("userName", "yamada");
        request.setParameter("email", "yamada@hoge.com");
        request.setParameter("password", "passhoge");
        request.setParameter("password_confirm", "passhoge");

        controller.doPost(request, response);

        User actual = repo(User.class).findFirstBy("email", "yamada@hoge.com");

        assertThat(actual.getEmail(), is("yamada@hoge.com"));
    }

    private void createUser() {
        User user = new User("mary@example.com");
        user.setPassword("abcd1234");
        user.save();
    }

}
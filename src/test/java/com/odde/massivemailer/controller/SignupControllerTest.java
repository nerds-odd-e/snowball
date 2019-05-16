package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.User;
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
        createUser();
    }


    @Test
    public void redirectTokkunPageFromSignup() throws Exception {
        request.setParameter("userName", "");
        request.setParameter("email", "");
        request.setParameter("password", "");
        request.setParameter("password_confirm", "");

        controller.doPost(request, response);
        assertThat(response.getRedirectedUrl(), CoreMatchers.containsString("tokkun/tokkun_top.jsp"));
    }


    // 新規会員登録ができる
    // 登録が成功したら、ログイン状態にすること
    // 登録に失敗したら、失敗しましたとエラーメッセージをだす

    @Test
    public void saveUserInfoToCookie() {

    }

    private void createUser() {
        User user = new User("mary@example.com");
        user.setPassword("abcd1234");
        user.saveIt();
    }

}
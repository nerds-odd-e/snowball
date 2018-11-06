package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.controller.onlinetest.AddQuestionController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(TestWithDB.class)
public class AddQuestionControllerTest {
    private AddQuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new AddQuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void validateDescription() throws Exception {
        controller.doPost(request, response);

        // assertThat(response.getContentAsString(), containsString("入力が不適切です"));
    }
}

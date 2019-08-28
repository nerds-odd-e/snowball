package com.odde.snowball.controller;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(TestWithDB.class)
public class DashboardControllerTest {
    private DashboardController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Category cat1 = Category.create("Cat1");
    private User currentUser = new User().save();

    @Before
    public void setUpMockService() {
        controller = new DashboardController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("currentUser", currentUser);
        Question question = new Question("description", "advice", cat1.getId(), false, true);
        question.save();
    }

    @Test
    public void showQuestion() throws Exception {
        controller.doGet(request, response);
        Question question = ((List<Question>)request.getAttribute("questions")).get(0);
        assertNotNull(question);
        assertEquals("description", question.getDescription());
    }
}
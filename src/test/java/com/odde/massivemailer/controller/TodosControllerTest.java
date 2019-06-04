package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(TestWithDB.class)
public class TodosControllerTest {
    private TodosController controller;
    private MockHttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        controller = new TodosController();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
    }

    @Test
    public void returnContactsInJSON() throws Exception {
        controller.doGet(req, res);
        assertThat(res.getRedirectedUrl(), is("todos.jsp"));
    }
}

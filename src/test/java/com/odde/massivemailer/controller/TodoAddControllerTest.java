package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Todo;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(TestWithDB.class)
public class TodoAddControllerTest {
    private TodoAddController controller;
    private MockHttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        controller = new TodoAddController();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
    }

    @Test
    public void redirectUrl() throws Exception {
        req.setContent("{\"title\": \"お風呂掃除\"}".getBytes());
        controller.doPost(req, res);
        assertEquals(res.getRedirectedUrl(), "todo.jsp");
    }


    @Test
    public void createTodo() throws Exception {
        req.setContent("{\"title\": \"お風呂掃除\"}".getBytes());
        controller.doPost(req, res);
        LazyList<Todo> all = Todo.findAll();
        assertEquals(1, all.size());
        assertEquals("お風呂掃除", all.get(0).get("title"));
        assertEquals("todo", all.get(0).get("status"));
    }
}

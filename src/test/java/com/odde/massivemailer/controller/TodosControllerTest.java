package com.odde.massivemailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.TestWithDB;
import com.odde.massivemailer.model.Todo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
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
    public void renderTodos() throws Exception {
        controller.doGet(req, res);
        assertThat(res.getRedirectedUrl(), is("todos.jsp"));
    }

    @Test
    public void setTodos() throws Exception {
        Todo.createIt("title", "craft beer", "status", "todo");
        Todo.createIt("title", "sake", "status", "doing");

        controller.doGet(req, res);

        String json = (String) req.getAttribute("json");
        assertThat(json, containsString("todo"));
        assertThat(json, containsString("doing"));
    }
}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Todo;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class TodoControllerTest {
    private TodoController controller;
    private MockHttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        controller = new TodoController();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
    }

    @Test
    public void doPost() throws Exception {
        req.setContent("{\"inputValue\": \"ohuro souji\"}".getBytes());
        controller.doPost(req, res);
        assertEquals(res.getStatus(),200);

        assertEquals(1,Todo.findAll().size());
        assertEquals("ohuro souji", Todo.findAll().get(0).get("title"));

    }
    @Test
    public void doGet() throws Exception {
        Todo.createIt("title","ohuro souji","status","0");
        controller.doGet(req, res);
        assertThat(res.getContentAsString(), CoreMatchers.containsString("\"title\":\"ohuro souji\""));
    }

}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;


@RunWith(TestWithDB.class)
public class SampleControllerTest {
    private SampleController controller;
    private MockHttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        controller = new SampleController();
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
    }

    @Test
    public void doGet() throws Exception {
        controller.doGet(req, res);
        assertThat(res.getContentAsString(), containsString("response!"));
    }

    @Test
    public void doPost() throws Exception {
        req.setContent("{\"key\": \"value\"}".getBytes());
        controller.doPost(req, res);
    }


}

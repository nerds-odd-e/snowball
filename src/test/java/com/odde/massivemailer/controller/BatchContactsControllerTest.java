package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class BatchContactsControllerTest {
    private BatchContactsController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new BatchContactsController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void returnListOfValidContacts() throws Exception {
        String contactPayload = "firstName,lastName;testFN,testLN";
        request.setContent(contactPayload.getBytes());
        controller.doPost(request, response);

        assertThat(response.getRedirectedUrl(), is("add_contact_batch.jsp"));
    }

}

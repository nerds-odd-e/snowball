package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import org.junit.Before;
import org.junit.Ignore;
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

        request.setParameter("data",
                "email,firstName,lastName,company,country,city;aaa@aaa.com,myname,mylastName,mycompany,singapore,mycity");
    }

    @Test
    public void willRedirect() throws Exception {
        String contactPayload = "firstName,lastName;testFN,testLN";
        request.setContent(contactPayload.getBytes());
        controller.doPost(request, response);
        assertThat(response.getContentAsString(), is(""));
        assertThat(response.getRedirectedUrl(), is("add_contact_batch.jsp"));
    }

    @Test
    public void willNotSaveContactPersonIfListIsEmpty() throws Exception {
        request.setParameter("data",
                "email,firstName,lastName,company,country,city");
        controller.doPost(request, response);
        assertThat(ContactPerson.repository().count(), is(0));
    }

    @Test
    public void willSaveSingletonContactPersonList() throws Exception {
        request.setParameter("data",
                "email,firstName,lastName,company,country,city;aaa@aaa.com,myname,mylastName,mycompany,singapore,mycity");
        controller.doPost(request, response);
        assertThat(ContactPerson.repository().count(), is(1));
    }
}
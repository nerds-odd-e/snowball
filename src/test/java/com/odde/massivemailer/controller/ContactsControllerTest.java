package com.odde.massivemailer.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.GMailService;
import org.javalite.activejdbc.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(TestWithDB.class)
public class ContactsControllerTest {
    private ContactsController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    private GMailService gmailService;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);

        controller = new ContactsController();
        controller.setMailService(gmailService);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void returnContactsInJSON() throws Exception {
        new ContactPerson("John", "john@gmail.com", "Doe", "ComA").saveIt();
        new ContactPerson("Peter", "peter@gmail.com", "Toh", "ComA").saveIt();

        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"email\":\"john@gmail.com\""));
        assertThat(response.getContentAsString(), containsString("\"firstname\":\"Peter\""));
    }

    @Test
    public void searchContactsInJSON() throws Exception {
        new ContactPerson("John", "john@gmail.com", "Doe", "ComA").saveIt();
        new ContactPerson("Peter", "peter@gmail.com", "Toh", "ComA").saveIt();
        request.setParameter("email", "john@gmail.com");
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"email\":\"john@gmail.com\""));
        Assert.assertTrue(response.getContentAsString().indexOf("\"email\":\"peter@gmail.com\"") < 0);
    }

    @Test
    public void addAnExistingContact() throws Exception {
        new ContactPerson("John", "john@gmail.com", "Doe", "ComA").saveIt();
        assertEquals(1, (long) ContactPerson.count());
        request.setParameter("email", "john@gmail.com");
        request.setParameter("country", "Singapore");
        request.setParameter("city", "Singapore");
        controller.doPost(request, response);
        assertEquals(1, (long) ContactPerson.count());
        assertEquals("contactlist.jsp?status=failed&msg={ email=<should be unique> }", response.getRedirectedUrl());
    }

    @Test
    public void addNewContact() throws Exception {
        request.setParameter("company", "odd-e");
        request.setParameter("lastname", "Smith");
        request.setParameter("name", "Mark");
        request.setParameter("email", "newbie@gmail.com");
        request.setParameter("country", "Singapore");
        request.setParameter("city", "Singapore");
        controller.doPost(request, response);

        verify(gmailService).send(any(Mail.class));
        assertEquals("contactlist.jsp?status=success&msg=Add contact successfully", response.getRedirectedUrl());

    }

}

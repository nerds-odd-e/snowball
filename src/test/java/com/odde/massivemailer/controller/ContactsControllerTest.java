package com.odde.massivemailer.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import com.odde.TestWithDB;
import org.javalite.activejdbc.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(TestWithDB.class)
public class ContactsControllerTest {
    private ContactsController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    @Before
    public void setUpMockService() {
        controller = new ContactsController();

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
    public void addNewContactWithoutConsentId() throws Exception {
        request.setParameter("company", "odd-e");
        request.setParameter("lastname", "Smith");
        request.setParameter("name", "Mark");
        request.setParameter("email", "newbie@gmail.com");
        request.setParameter("country", "Singapore");
        request.setParameter("city", "Singapore");
        controller.doPost(request, response);

        assertEquals("contactlist.jsp?status=success&msg=Add contact successfully", response.getRedirectedUrl());
    }

    @Test
    public void addNewContactWithConsentId() throws Exception {
        request.setParameter("company", "odd-e");
        request.setParameter("lastname", "Smith");
        request.setParameter("name", "Mark");
        request.setParameter("email", "newbie@gmail.com");
        request.setParameter("country", "Singapore");
        request.setParameter("city", "Singapore");
        request.setParameter("consent_id", "0f60ada7d76eaa22651648cb39c349d5");
        controller.doPost(request, response);

        assertEquals("contactlist.jsp?status=success&msg=Add contact successfully with existing consent_id", response.getRedirectedUrl());
        assertEquals(1, (long) ContactPerson.count());
        Model lastContact = ContactPerson.findFirst("1 = 1");
        assertEquals("Singapore/Singapore",lastContact.getString("location"));
        assertEquals("0f60ada7d76eaa22651648cb39c349d5",lastContact.getString("consent_id"));
    }

}

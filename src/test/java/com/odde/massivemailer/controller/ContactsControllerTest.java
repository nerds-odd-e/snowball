package com.odde.massivemailer.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ContactsControllerTest {
    private ContactsController controller;
    private ContactService contactService;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private List<ContactPerson> contacts;

    @Before
    public void setUpMockService() {
        contactService = mock(SqliteContact.class);

        controller = new ContactsController(contactService);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        contacts = new ArrayList<>();
        contacts.add(new ContactPerson("John", "john@gmail.com", "Doe", "ComA"));
        contacts.add(new ContactPerson("Peter", "peter@gmail.com", "Toh", "ComA"));
    }

    @Test
    public void returnContactsInJSON() throws Exception {
        when(contactService.getContactList()).thenReturn(contacts);

        controller.doGet(request, response);

        assertEquals(new Gson().toJson(contacts), response.getContentAsString());
    }

    @Test
    public void addAnExistingContact() throws Exception {
        when(contactService.addContact(any(ContactPerson.class))).thenReturn(false);

        request.setParameter("email", "john@gmail.com");
        controller.doPost(request, response);

        assertEquals("contactlist.jsp?status=failed&msg=Email john@gmail.com is already exist", response.getRedirectedUrl());
    }

    @Test
    public void addNewContact() throws Exception {
        when(contactService.addContact(any(ContactPerson.class))).thenReturn(true);

        request.setParameter("email", "newbie@gmail.com");
        controller.doPost(request, response);

        assertEquals("contactlist.jsp?status=success&msg=Add contact successfully", response.getRedirectedUrl());
    }
}

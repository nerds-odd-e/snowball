package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.odde.massivemailer.controller.ContactsController;
import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;

public class ContactsControllerTest {
    ContactsController contactsController;
    ContactService contactService;
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    List<ContactPerson> contacts = new ArrayList<ContactPerson>();

    @Before
    public void setUpMockService() {
        contactService = mock(SqliteContact.class);
        contactsController = new ContactsController(contactService);

        contacts.add(new ContactPerson("John", "john@gmail.com", "Doe"));
        contacts.add(new ContactPerson("Peter", "peter@gmail.com", "Toh"));
    }

    @Test
    public void returnContactsInJSON() throws Exception {
        when(contactService.getContactList()).thenReturn(contacts);
        contactsController.doGet(req, res);
        assertEquals(new Gson().toJson(contacts),res.getContentAsString());
    }

    @Test
    public void addAnExistingContact() throws Exception {
        req.setParameter("email", "john@gmail.com");
        when(contactService.addContact(any(ContactPerson.class))).thenReturn(false);
        contactsController.doPost(req, res);
        assertEquals("contactlist.jsp?status=failed&msg=Email john@gmail.com is already exist", res.getRedirectedUrl());
    }

    @Test
    public void addNewContact() throws Exception {
        req.setParameter("email", "newbie@gmail.com");
        when(contactService.addContact(any(ContactPerson.class))).thenReturn(true);
        contactsController.doPost(req, res);
        assertEquals("contactlist.jsp?status=success&msg=Add contact successfully", res.getRedirectedUrl());
    }
}

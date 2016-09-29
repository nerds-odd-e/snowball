package com.odde.emersonsgame.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class GameLoginControllerTest {
    GameLoginController gameLoginController;
    ContactService contactService;
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    List<ContactPerson> contacts = new ArrayList<ContactPerson>();

    @Before
    public void setUpMockService() {
        contactService = mock(SqliteContact.class);

        gameLoginController = new GameLoginController(contactService);

        contacts.add(new ContactPerson("John", "john@gmail.com", "Doe", "ComA"));
        contacts.add(new ContactPerson("Peter", "peter@gmail.com", "Toh", "ComA"));
    }

    @Test
    public void loginWithValidEmail() throws Exception {
        req.setParameter("email", "john@gmail.com");
        ArgumentCaptor<ContactPerson> argument = ArgumentCaptor.forClass(ContactPerson.class);
        gameLoginController.doPost(req, res);

        verify(contactService).addContact(argument.capture());
        assertEquals("john@gmail.com", argument.getValue().getEmail());
        assertEquals("EmersonsGame", res.getRedirectedUrl());
        assertTrue("Should contain email", req.getSession().getAttribute("email").equals("john@gmail.com"));
    }

    @Test
    public void loginWithInvalidEmail() throws Exception {
        req.setParameter("email", "john");
        gameLoginController.doPost(req, res);

        verify(contactService, never()).addContact(any(ContactPerson.class));
        assertEquals("game_login.jsp?error=Invalid+email+provided!", res.getRedirectedUrl());
    }

}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class UpdateContactControllerTest {
    private UpdateContactController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new UpdateContactController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void editExistingContact() throws Exception {
        new ContactPerson().set(
                "firstname", "John",
                "email", "john@gmail.com",
                "lastname", "Doe",
                "company", "ComA",
                "city", "Singapore",
                "country", "Singapore").saveIt();
        assertEquals(1, (long) ContactPerson.count());
        request.setParameter("email", "john@gmail.com");
        request.setParameter("country", "China");
        request.setParameter("city", "Chengdu");
        request.setParameter("name", "Jack");
        request.setParameter("company", "ComB");
        request.setParameter("lastname", "Dale");

        controller.doPost(request, response);
        assertEquals(1, (long) ContactPerson.count());
        ContactPerson contact = ContactPerson.getContactByEmail("john@gmail.com");
        assertEquals("Jack", contact.get("FirstName"));
        assertEquals("ComB", contact.get("Company"));
        assertEquals("China/Chengdu", contact.get("Location"));
        assertEquals("Dale", contact.get("LastName"));

        assertEquals("contactlist.jsp", response.getRedirectedUrl());
    }
}

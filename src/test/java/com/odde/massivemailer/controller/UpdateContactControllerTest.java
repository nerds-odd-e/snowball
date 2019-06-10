package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.odde.massivemailer.model.base.Repository.repo;
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
        repo(ContactPerson.class).fromKeyValuePairs(
                "firstName", "John",
                "email", "john@gmail.com",
                "lastName", "Doe",
                "company", "ComA",
                "city", "Singapore",
                "country", "Singapore").save();
        assertEquals(1, (long) repo(ContactPerson.class).count());
        request.setParameter("email", "john@gmail.com");
        request.setParameter("country", "China");
        request.setParameter("city", "Chengdu");
        request.setParameter("name", "Jack");
        request.setParameter("company", "ComB");
        request.setParameter("lastName", "Dale");

        controller.doPost(request, response);
        assertEquals(1, (long) repo(ContactPerson.class).count());
        ContactPerson contact = ContactPerson.getContactByEmail("john@gmail.com");
        assertEquals("Jack", contact.getFirstName());
        assertEquals("ComB", contact.getCompany());
        assertEquals("China/Chengdu", contact.location());
        assertEquals("Dale", contact.getLastName());

        assertEquals("contactlist.jsp", response.getRedirectedUrl());
    }
}

package com.odde.massivemailer.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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
        new ContactPerson("John", "john@gmail.com", "Doe", "ComA", "Singapore").saveIt();
        assertEquals(1, (long) ContactPerson.count());
        request.setParameter("email", "john@gmail.com");
        request.setParameter("location", "Malaysia");
        request.setParameter("name", "Jack");
        request.setParameter("company", "ComB");
        request.setParameter("lastname", "Dale");

        controller.doPost(request, response);
        assertEquals(1, (long) ContactPerson.count());
        ContactPerson contact = ContactPerson.getContactByEmail("john@gmail.com");
        for (String attribute : contact.getAttributeKeys()) {
            switch (attribute) {
                case "FirstName":
                    assertEquals("Jack",contact.get(attribute));
                case "Company":
                    assertEquals("ComB",contact.get(attribute));
                case "Location":
                    assertEquals("Malaysia",contact.get(attribute));
                case "LastName":
                    assertEquals("Dale",contact.get(attribute));

            }
        }

        assertEquals("contactlist.jsp", response.getRedirectedUrl());
    }
}

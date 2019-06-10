package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.service.GMailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static com.odde.massivemailer.factory.ContactFactory.uniqueContact;
import static com.odde.massivemailer.model.base.Repository.repo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;


@RunWith(TestWithDB.class)
public class ContactsControllerTest {
    private ContactsController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    private GMailService gmailService;

	@Captor
    private ArgumentCaptor<Mail> mailCaptor;

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
        ContactPerson contactPerson1 = uniqueContact();
        ContactPerson contactPerson2 = uniqueContact();
        contactPerson1.save();
        contactPerson2.save();

        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"email\":\""+contactPerson1.getEmail()+"\""));
        assertThat(response.getContentAsString(), containsString("\"firstName\":\""+ contactPerson2.getFirstName() +"\""));
    }

    @Test
    public void searchContactsInJSON() throws Exception {
        ContactPerson contactPerson1 = uniqueContact();
        ContactPerson contactPerson2 = uniqueContact();
        contactPerson1.save();
        contactPerson2.save();
        request.setParameter("email", contactPerson1.getEmail());
        controller.doGet(request, response);

        assertThat(response.getContentAsString(), containsString("\"email\":\""+contactPerson1.getEmail()+"\""));
        assertTrue(!response.getContentAsString().contains("\"email\":\""+contactPerson2.getEmail()+"m\""));
    }

    @Test
    public void addAnExistingContact() throws Exception {
        ContactPerson contactPerson1 = uniqueContact();
        contactPerson1.save();
        assertEquals(1, (long) repo(ContactPerson.class).count());
        request.setParameter("email", contactPerson1.getEmail());
        request.setParameter("country", "Singapore");
        request.setParameter("city", "Singapore");
        controller.doPost(request, response);
        assertEquals(1, (long) repo(ContactPerson.class).count());
        assertEquals("add_contact.jsp?status=fail&msg={ email:\"should be unique\" }", response.getRedirectedUrl());
    }

    @Test
    public void addNewContact() throws Exception {
        request.setParameter("company", "odd-e");
        request.setParameter("lastName", "Smith");
        request.setParameter("firstName", "Mark");
        request.setParameter("email", "newbie@example.com");
        request.setParameter("country", "Singapore");
        request.setParameter("city", "Singapore");
        controller.doPost(request, response);

        assertEquals("contactlist.jsp?status=success&msg=Add contact successfully", response.getRedirectedUrl());

        User user = repo(User.class).findFirstBy("email", request.getParameter("email"));
        assertNotNull(user);
    }

    @Test
    public void mustSendAnEmailContainingPasswordSettingURLWithTokenWhenAddContact() throws IOException {
        request.setParameter("company", "odd-e");
        request.setParameter("lastName", "Smith");
        request.setParameter("firstName", "Mark");
        request.setParameter("email", "newbie@gmail.com");
        request.setParameter("country", "Singapore");
        request.setParameter("city", "Singapore");

        controller.doPost(request, response);

		verify(gmailService).send(mailCaptor.capture());
		Mail mail = mailCaptor.getValue();

        assertThat(mail.getContent(), containsString("http://localhost:8070/initialPassword?token="));
    }
}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.service.GMailService;
import com.odde.massivemailer.startup.Universe;
import org.javalite.activejdbc.LazyList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(TestWithDB.class)
public class SendMailControllerTest {
    private SendMailController controller;

    private GMailService gmailService;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;


    @Before
    public void setUp() throws ServletException {
        gmailService = mock(GMailService.class);

        Universe.createUniverse(
                new Universe.MockConfiguration()
                        .with(gmailService)
        );

        controller = new SendMailController();
        controller.init(mock(ServletConfig.class));
        controller.setMailService(gmailService);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setParameter("content", "content-na-ka");
        request.setParameter("subject", "subject for test");
    }

    @Test
    public void testPostSuccessful() throws Exception {
        mockRecipient("whatever");

        controller.doPost(request, response);
        verify(gmailService).send(any(Mail.class));
    }

    @Test
    public void testPostEmailException() throws Exception {
        mockRecipient("whatever");

        doThrow(new EmailException("")).when(gmailService).send(any(Mail.class));
        controller.doPost(request, response);
        assertEquals("sendemail.jsp?status=failed&msg=Unable to send", response.getRedirectedUrl());
    }



    @Test
    public void testProcessRequest() throws SQLException {

        mockRecipient("name1@gmail.com", "name2@gmail.com");
        String subject = "subject for test";
        String content = "content-na-ka";

        List<String> recipientList = controller.getRecipientList(request);
        Mail mail = controller.createEmail(System.currentTimeMillis(),content,subject,recipientList);

        assertEquals(subject, mail.getSubject());
        List<String> repList = mail.getReceipts();
        assertEquals("name1@gmail.com", repList.get(0));
        assertEquals("name2@gmail.com", repList.get(1));
        assertEquals(content, mail.getContent());
    }


    @Test
    public void testProcessRequestByCompany() throws SQLException {

        mockRecipient("company:abc");
        String[] companyRecipients = {"ab1@abc.com", "ab2@abc.com", "ab3@abc.com"};

        for (int i = 0; i < companyRecipients.length; ++i) {
            new ContactPerson("", companyRecipients[i], "", "abc").saveIt();
        }

        List<String> recipientList = controller.getRecipientList(request);
        Mail mail = controller.createEmail(System.currentTimeMillis(),"bla","bbla",recipientList);


        List<String> repList = mail.getReceipts();
        for (int i = 0; i < repList.size(); ++i) {
            assertEquals(companyRecipients[i], repList.get(i));
        }

    }

    @Test
    public void ProcessRequestMustHandleCompany() throws SQLException {
        String company = "abc";

        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:" + company);

        List<String> recipientList = controller.getRecipientList(request);
        Mail mail = controller.createEmail(System.currentTimeMillis(),"bla","bbla",recipientList);

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    @Test
    public void ProcessRequestMustHandleCompanyWithSpace() throws SQLException {
        String company = "abc def";

        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:\"" + company + "\"");

        List<String> recipientList = controller.getRecipientList(request);
        Mail mail = controller.createEmail(System.currentTimeMillis(),"bla","bbla",recipientList);

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.size(), is(3));
        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    private class ContactPeopleBuilder {
        private String company;

        public ContactPeopleBuilder(final String company) {
            this.company = company;
        }

        public ContactPeopleBuilder add(final String email) {
            new ContactPerson("", email, "", company).saveIt();
            return this;
        }
    }

    @Test
    public void ProcessRequestMustHandleCompanyWithSpaceButWtf() throws SQLException {
        String company = "abc def";
        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:\"" + company);

        List<String> recipientList = controller.getRecipientList(request);
        Mail mail = controller.createEmail(System.currentTimeMillis(),"bla","bbla",recipientList);


        List<String> recipients = mail.getReceipts();

        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    @Test
    public void ProcessRequestMustHandleCompanyWithSpaceButNoQuotesWtf() throws SQLException {
        String company = "abc def";

        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:" + company);

        List<String> recipientList = controller.getRecipientList(request);
        Mail mail = controller.createEmail(System.currentTimeMillis(),"bla","bbla",recipientList);

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    @Test
    public void SendMailMustSaveNotification() throws IOException {
        mockRecipient("terry@odd-e.com");

        controller.doPost(request, response);

        LazyList<SentMail> all = SentMail.findAll();
        SentMail capturedSentMail =  all.get(all.size() - 1);

        assertNotNull(capturedSentMail.getMessageId());
        assertThat(capturedSentMail.getSubject(), is("subject for test"));
    }

    private List<ContactPerson> mockRecipient(String... emails) {
        List<ContactPerson> contacts =
                Arrays.asList(emails).stream()
                        .map(email -> {
                            ContactPerson person = new ContactPerson(email, email, email);
                            person.saveIt();
                            return person;
                        })
                        .collect(Collectors.toList());

        String recipients = contacts.stream()
                .map(ContactPerson::getEmail)
                .collect(Collectors.joining(";"));
        request.setParameter("recipient", recipients);

        return contacts;
    }

    @Test
    public void sendEmailToDeletedContactPerson() throws IOException {
        String email = "terry@odde.com";
        List<ContactPerson> contacts = mockRecipient(email);
        contacts.stream().forEach(contact -> {
            contact.setForgotten(true);
            contact.saveIt();
        });

        controller.doPost(request, response);
        verify(gmailService, times(0)).send(any(Mail.class));
    }
}

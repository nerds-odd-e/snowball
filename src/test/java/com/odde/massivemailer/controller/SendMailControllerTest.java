package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.SentMail;
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
import java.util.List;

import static com.odde.massivemailer.model.base.Repository.repo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;


@RunWith(TestWithDB.class)
public class SendMailControllerTest {
    private SendMailController controller;

    @Mock
    private GMailService gmailService;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    @Captor
    private ArgumentCaptor<Mail> mailCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new SendMailController();
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
        assertEquals("/admin/sendemail.jsp?status=failed&msg=Unable to send", response.getRedirectedUrl());
    }



    @Test
    public void testProcessRequest() {

        mockRecipient("name1@gmail.com;name2@gmail.com");
        Mail mail = postAndGetMailBeingSent();

        assertEquals("subject for test", mail.getSubject());
        List<String> repList = mail.getReceipts();
        assertEquals("name1@gmail.com", repList.get(0));
        assertEquals("name2@gmail.com", repList.get(1));
        assertEquals("content-na-ka", mail.getContent());
    }

    private Mail postAndGetMailBeingSent() {
        try {
            controller.doPost(request, response);
            verify(gmailService).send(mailCaptor.capture());
        } catch (IOException e) {
           fail();
        }
        return mailCaptor.getValue();
    }


    @Test
    public void testProcessRequestByCompany() {

        mockRecipient("company:abc");
        String[] companyRecipients = {"ab1@abc.com", "ab2@abc.com", "ab3@abc.com"};

        for (String companyRecipient : companyRecipients) {
            repo(ContactPerson.class).fromKeyValuePairs("email", companyRecipient, "company", "abc").saveIt();
        }

        Mail mail = postAndGetMailBeingSent();


        List<String> repList = mail.getReceipts();
        for (int i = 0; i < repList.size(); ++i) {
            assertEquals(companyRecipients[i], repList.get(i));
        }

    }

    @Test
    public void ProcessRequestMustHandleCompany() {
        String company = "abc";

        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:" + company);

        Mail mail = postAndGetMailBeingSent();

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    @Test
    public void ProcessRequestMustHandleCompanyWithSpace() {
        String company = "abc def";

        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:\"" + company + "\"");

        Mail mail = postAndGetMailBeingSent();

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.size(), is(3));
        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    private class ContactPeopleBuilder {
        private final String company;

        ContactPeopleBuilder(final String company) {
            this.company = company;
        }

        ContactPeopleBuilder add(final String email) {
            repo(ContactPerson.class).fromKeyValuePairs("email", email, "company", company).saveIt();
            return this;
        }
    }

    @Test
    public void ProcessRequestMustHandleCompanyWithSpaceButWtf() {
        String company = "abc def";
        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:\"" + company);

        Mail mail = postAndGetMailBeingSent();

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    @Test
    public void ProcessRequestMustHandleCompanyWithSpaceButNoQuotesWtf() {
        String company = "abc def";

        new ContactPeopleBuilder(company)
                .add("ab1@abc.com")
                .add("ab2@abc.com")
                .add("ab3@abc.com");

        mockRecipient("company:" + company);

        Mail mail = postAndGetMailBeingSent();

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    @Test
    public void SendMailMustSaveNotification() throws IOException {
        mockRecipient("terry@odd-e.com");

        controller.doPost(request, response);

        List<SentMail> all = repo(SentMail.class).findAll();
        SentMail capturedSentMail =  all.get(all.size() - 1);

        assertNotNull(capturedSentMail.getMessageId());
        assertThat(capturedSentMail.getSubject(), is("subject for test"));
    }

    private void mockRecipient(String recipient){
        request.setParameter("recipient", recipient);
    }
}

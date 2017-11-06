package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.service.GMailService;
import org.javalite.activejdbc.LazyList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(TestWithDB.class)
public class SendMailControllerTest {
    private SendMailController controller;

    @Mock
    private GMailService gmailService;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    @Captor
    private ArgumentCaptor<Notification> notificationCaptor;


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
        assertEquals("sendemail.jsp?status=failed&msg=Unable to send", response.getRedirectedUrl());
    }



    @Test
    public void testProcessRequest() throws SQLException {

        mockRecipient("name1@gmail.com;name2@gmail.com");
        Mail mail = controller.processRequest(request);

        assertEquals("subject for test", mail.getSubject());
        List<String> repList = mail.getReceipts();
        assertEquals("name1@gmail.com", repList.get(0));
        assertEquals("name2@gmail.com", repList.get(1));
        assertEquals("content-na-ka", mail.getContent());
    }


    @Test
    public void testProcessRequestByCompany() throws SQLException {

        mockRecipient("company:abc");
        String[] companyRecipients = {"ab1@abc.com", "ab2@abc.com", "ab3@abc.com"};

        for (int i = 0; i < companyRecipients.length; ++i) {
            new ContactPerson("", companyRecipients[i], "", "abc").saveIt();
        }

        Mail mail = controller.processRequest(request);

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

        Mail mail = controller.processRequest(request);

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

        Mail mail = controller.processRequest(request);

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

        Mail mail = controller.processRequest(request);

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

        Mail mail = controller.processRequest(request);

        List<String> recipients = mail.getReceipts();

        assertThat(recipients.get(0), is("ab1@abc.com"));
        assertThat(recipients.get(1), is("ab2@abc.com"));
        assertThat(recipients.get(2), is("ab3@abc.com"));
    }

    @Test
    public void SendMailMustSaveNotification() throws ServletException, IOException {
        mockRecipient("terry@odd-e.com");

        controller.doPost(request, response);

        LazyList<Notification> all = Notification.findAll();
        Notification capturedNotification =  all.get(all.size() - 1);

        assertNotNull(capturedNotification.getMessageId());
        assertThat(capturedNotification.getSubject(), is("subject for test"));
    }

    private void mockRecipient(String recipient){
        request.setParameter("recipient", recipient);
    }
}

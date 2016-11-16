package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.service.NotificationService;
import com.odde.massivemailer.service.impl.GMailService;
import com.odde.massivemailer.service.impl.SqliteContact;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class SendMailControllerTest {
    private SendMailController controller;
    private SqliteContact contactService;
    private GMailService gmailService;
    private NotificationService notificationService;

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Captor
    private ArgumentCaptor<Notification> notificationCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new SendMailController();

        contactService = mock(SqliteContact.class);
        controller.setSqliteContact(contactService);

        gmailService = mock(GMailService.class);
        controller.setGmailService(gmailService);

        notificationService = mock(NotificationService.class);
        controller.setNotificationService(notificationService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        when(request.getParameter("content")).thenReturn("content-na-ka");
        when(request.getParameter("subject")).thenReturn("subject for test");
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
        verify(response).sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");
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

        List<ContactPerson> contactPersonList = new ArrayList<>();
        for (int i = 0; i < companyRecipients.length; ++i) {
            contactPersonList.add(new ContactPerson("", companyRecipients[i], "", "abc"));
        }

        when(contactService.getContactListFromCompany("abc")).thenReturn(contactPersonList);

        Mail mail = controller.processRequest(request);

        List<String> repList = mail.getReceipts();
        for (int i = 0; i < repList.size(); ++i) {
            assertEquals(companyRecipients[i], repList.get(i));
        }

    }

    @Test
    public void SendMailMustSaveNotification() throws ServletException, IOException {
        mockRecipient("terry@odd-e.com");

        controller.doPost(request, response);

        verify(notificationService).save(notificationCaptor.capture());

        Notification capturedNotification = notificationCaptor.getValue();

        assertEquals("subject for test", capturedNotification.getSubject());
        assertNotNull(capturedNotification.getNotificationId());
    }

    private void mockRecipient(String recipient){
        when(request.getParameter("recipient")).thenReturn(recipient);
    }
}

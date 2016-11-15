package com.odde.massivemailer.controller;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.service.impl.GMailService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.impl.SqliteContact;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;


public class SendMailControllerTest {

    HttpServletRequest httpReq = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse httpResp = Mockito.mock(HttpServletResponse.class);
    SqliteContact mockedContact = Mockito.mock(SqliteContact.class);
    SendMailController mailController = new SendMailController();


    @Before
    public void setup() {
        Mockito.when(httpReq.getParameter("content")).thenReturn("content-na-ka");
        Mockito.when(httpReq.getParameter("subject")).thenReturn("subject for test");
    }

    @Test
    public void testPostSuccessful() throws Exception {
        GMailService mockGmailService = Mockito.mock(GMailService.class);
        mailController.setGmailService(mockGmailService);
        mockRecipient("whatever");

        mailController.doPost(httpReq, httpResp);
        Mockito.verify(mockGmailService).send(any(Mail.class));
    }

    @Test
    public void testPostEmailException() throws Exception {
        GMailService mockGmailService = Mockito.mock(GMailService.class);
        mailController.setGmailService(mockGmailService);
        mockRecipient("whatever");

        Mockito.doThrow(new EmailException("")).when(mockGmailService).send(any(Mail.class));
        mailController.doPost(httpReq, httpResp);
        Mockito.verify(httpResp).sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");
    }



    @Test
    public void testProcessRequest() throws SQLException {

        mockRecipient("name1@gmail.com;name2@gmail.com");
        Mail mail = mailController.processRequest(httpReq);

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

        Mockito.when(mockedContact.getContactListFromCompany("abc")).thenReturn(contactPersonList);

        mailController.setSqliteContact(mockedContact);
        Mail mail = mailController.processRequest(httpReq);

        List<String> repList = mail.getReceipts();
        for (int i = 0; i < repList.size(); ++i) {
            assertEquals(companyRecipients[i], repList.get(i));
        }

    }

    private void mockRecipient(String recipient){
        Mockito.when(httpReq.getParameter("recipient")).thenReturn(recipient);
    }
}

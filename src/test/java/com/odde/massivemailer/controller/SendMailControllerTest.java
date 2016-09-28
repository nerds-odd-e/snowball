package com.odde.massivemailer.controller;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.mockito.Mockito;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.impl.SqliteContact;


public class SendMailControllerTest {

    HttpServletRequest httpReq = Mockito.mock(HttpServletRequest.class);
    SqliteContact mockedContact = Mockito.mock(SqliteContact.class);
    SendMailController mailController = new SendMailController();

    @org.junit.Before
    public void setup() {
        Mockito.when(httpReq.getParameter("content")).thenReturn("content-na-ka");
        Mockito.when(httpReq.getParameter("subject")).thenReturn("subject for test");
    }

    @org.junit.Test
    public void testProcessRequest() throws SQLException {

        mockRecipient("name1@gmail.com;name2@gmail.com");
        Mail mail = mailController.processRequest(httpReq);

        Assert.assertEquals("subject for test", mail.getSubject());
        List<String> repList = mail.getReceipts();
        Assert.assertEquals("name1@gmail.com", repList.get(0));
        Assert.assertEquals("name2@gmail.com", repList.get(1));
        Assert.assertEquals("content-na-ka", mail.getContent());
    }


    @org.junit.Test
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
            Assert.assertEquals(companyRecipients[i], repList.get(i));
        }

    }

    private void mockRecipient(String recipient){
        Mockito.when(httpReq.getParameter("recipient")).thenReturn(recipient);
    }
}

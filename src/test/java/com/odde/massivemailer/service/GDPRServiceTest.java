package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.exception.GeoServiceException;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(TestWithDB.class)
public class GDPRServiceTest {

    MailService mockMailService = Mockito.mock(MailService.class);
    TemplateService templateService = Mockito.mock(TemplateService.class);
    GDPRService gdprService = new GDPRService(mockMailService, templateService);

    ArgumentCaptor<Mail> mailArgumentCaptor;

    @After
    public void tearDown() {
        ContactPerson.deleteAll();
    }

    @Test
    public void should_invoke_send_for_contact_without_consent_requested_date() throws EmailException, GeoServiceException {
        ContactPerson.deleteAll();
        ContactPerson.createContact("SG", "SG", "abc1@email.com", "john","doe","odde");

        ContactPerson contactPerson1 = new ContactPerson("SG", "SG", "abc4@email.com");
        contactPerson1.setConsentSend(LocalDate.now());
        contactPerson1.saveIt();

        gdprService.sendConsentRequestEmail();
        Mockito.verify(mockMailService, times(1)).send(Mockito.any(Mail.class));
    }

    @Test
    public void should_NOT_invoke_send_contact_with_consent_requested_date() throws EmailException, GeoServiceException {
        ContactPerson.deleteAll();
        ContactPerson contactPerson1 = new ContactPerson("SG", "SG", "abc4@email.com");
        contactPerson1.setConsentSend(LocalDate.now());
        contactPerson1.saveIt();

        gdprService.sendConsentRequestEmail();
        Mockito.verify(mockMailService, times(0)).send(Mockito.any(Mail.class));
    }

    @Ignore
    public void getEmailTest() {
        final List<Mail> mockEmails = new ArrayList<>();
        mockEmails.add(new Mail());

        final Collection<Mail> emails = gdprService.getEmails();
        assertThat(emails).isEqualTo(mockEmails);
    }

}

package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.exception.GeoServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(TestWithDB.class)
public class GDPRServiceTest {

    GDPRService gdprService;
    MailService mockMailService;

    ArgumentCaptor<Mail> mailArgumentCaptor;

    @Before
    public void setup() throws GeoServiceException {
        mockMailService = Mockito.mock(MailService.class);
        gdprService = new GDPRService(mockMailService);

        ContactPerson.deleteAll();
        ContactPerson.createContact("SG", "SG", "abc1@email.com");
        ContactPerson.createContact("SG", "SG", "abc2@email.com");
        ContactPerson.createContact("SG", "SG", "abc3@email.com");

        // create some with consent request date populated
        ContactPerson contactPerson1 = new ContactPerson("SG", "SG", "abc4@email.com");
        contactPerson1.setConsentRequestDate(LocalDate.now());
        contactPerson1.saveIt();
    }

    @After
    public void tearDown() {
        ContactPerson.deleteAll();
    }

    @Test
    public void should_invoke_send_for_all_contacts_without_consent_requested_date() throws EmailException {
        gdprService.sendConsentRequestEmail();

        Mockito.verify(mockMailService, times(3)).send(Mockito.any(Mail.class));
   }

   @Test
   public void should_invoke_send_email_for_abc1_abc2_abc3() throws EmailException {
       gdprService.sendConsentRequestEmail();

       Mockito.verify(mockMailService).send(mailArgumentCaptor.capture());
       List<Mail> mailList = mailArgumentCaptor.getAllValues();
       List<String> recipients = mailList.stream().flatMap(mail -> mail.getReceipts().stream()).collect(toList());
       ßßß
       assertThat(mailList)
               .has()

   }
}
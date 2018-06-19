package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(TestWithDB.class)
public class ConsentServiceTest {

    private static final LocalDate NOW = LocalDate.now();
    private ConsentService consentService;
    private Supplier<LocalDate> dateSupplier;

    @Before
    public void setup() {
        dateSupplier = mock(Supplier.class);
        consentService = new ConsentService(dateSupplier);

        mockCurrentDate(NOW);
    }

    private OngoingStubbing<LocalDate> mockCurrentDate(LocalDate date) {
        return when(dateSupplier.get()).thenReturn(date);
    }

    @Test
    public void updateConsentTest() {
        ContactPerson person = createPerson("email-test@abc.com");
        assertThat(person.getConsentSend()).isNull();

        LocalDate now = LocalDate.now();
        consentService.updateConsentRequestDate(person);

        final LocalDate consentRequestDate = person.getConsentSend();
        assertThat(consentRequestDate).isNotNull();
        assertThat(consentRequestDate).isEqualTo(now);
    }

    @Test
    public void updateConsentTestMoreThanOnce() {
        ContactPerson person = createPerson("email-test2@abc.com");

        LocalDate yesterday = LocalDate.now().minusDays(1);
        mockCurrentDate(yesterday);
        consentService.updateConsentRequestDate(person);
        assertThat(person.getConsentSend()).isEqualTo(yesterday);


        LocalDate now = LocalDate.now();
        mockCurrentDate(now);
        consentService.updateConsentRequestDate(person);
        assertThat(person.getConsentSend()).isEqualTo(yesterday);
    }

    @Test
    public void saveToDbTest() {
        final String email = "saveToDbTest@abc.com";
        ContactPerson person = createPerson(email);
        person.saveIt();
        final Object id = person.getId();
        consentService.updateConsentRequestDate(person);

        final ContactPerson contactFromDb = ContactPerson.findById(id);
        assertThat(contactFromDb).isNotNull();
        assertThat(contactFromDb.getConsentSend()).isNotNull();
        assertThat(contactFromDb.getConsentSend()).isEqualTo(NOW);
    }

    private ContactPerson createPerson(String email) {
        String name = "name1";
        String lastname = "lastname1";
        return new ContactPerson(name, email, lastname);
    }


}

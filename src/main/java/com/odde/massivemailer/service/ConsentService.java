package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;

import java.time.LocalDate;
import java.util.function.Supplier;

public class ConsentService {

    private static final Supplier<LocalDate> LOCAL_DATE_SUPPLIER = LocalDate::now;

    private final Supplier<LocalDate> dateSupplier;

    public ConsentService() {
        this(LOCAL_DATE_SUPPLIER);
    }

    public ConsentService(Supplier<LocalDate> dateSupplier) {
        this.dateSupplier = dateSupplier;
    }

    public void updateConsentRequestDate(ContactPerson contact) {
        if (contact.getConsentSend() == null) {
            update(contact);
        }
    }

    private void update(ContactPerson contact) {
        contact.setConsentSend(dateSupplier.get());
        contact.saveIt();
    }
}

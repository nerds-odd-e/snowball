package com.odde.snowball.factory;

import com.odde.snowball.model.ContactPerson;

import static com.odde.snowball.model.base.Repository.repo;

public class ContactFactory {
    private static int uniq_id = 0;

    public static ContactPerson uniqueContact() {
        uniq_id += 1;
        return repo(ContactPerson.class).fromKeyValuePairs(
                "firstName", "testName" + uniq_id,
                "email", "test" + uniq_id +"@gmail.com",
                "lastName", "test1LastName");
    }

    public static ContactPerson aContactFrom(String city, String country) {
        ContactPerson contact = uniqueContact();
        contact.setCity(city);
        contact.setCountry(country);
        return contact;
    }

}
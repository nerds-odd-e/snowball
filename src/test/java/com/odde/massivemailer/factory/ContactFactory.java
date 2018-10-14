package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.ContactPerson;

public class ContactFactory {
    private static int uniq_id = 0;

    public static ContactPerson uniqueContact() {
        uniq_id += 1;
        return ContactPerson.create(
                "firstname", "testName" + uniq_id,
                "email", "test" + uniq_id +"@gmail.com",
                "lastname", "test1LastName");
    }
}
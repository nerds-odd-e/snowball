package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

@Table("COURSE_CONTACT_PERSON")
public class Participant extends ApplicationModel {

    public Participant() {
    }

    public Participant(Integer _contactPersonId, Integer _courseId) {
        set("course_id", _courseId);
        set("contact_person_id", _contactPersonId);
    }

    ContactPerson getContactPerson() {
        return ContactPerson.findById((Integer) get("contact_person_id"));
    }

    Course getCourse() {
        return Course.findById((Integer) get("course_id"));
    }
}
package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

import java.util.List;

@Table("COURSE_CONTACT_PERSON")
public class Participant extends ApplicationModel {

    public Participant() {
    }

    public Participant(Integer _contactPersonId, Integer _courseId) {
        setCourseId(_courseId);
        setContactPersonId(_contactPersonId);
    }

    public void setCourseId(Integer _courseId) {
        set("course_id", _courseId);
    }

    public Integer getCourseId() {
        return (Integer) get("course_id");
    }

    public void setContactPersonId(Integer _contactPersonId) {
        set("contact_person_id", _contactPersonId);
    }

    public Integer getContactPersonId() {
        return (Integer) get("contact_person_id");
    }

}
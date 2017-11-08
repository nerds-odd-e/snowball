package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.omg.CORBA.BAD_CONTEXT;

import java.util.List;

@Table("COURSE_CONTACT_PERSON")
public class Participant extends ApplicationModel {

    public Participant() {
    }

    public Participant(Integer _participantId, Integer _courseId) {
        setCourseId(_courseId);
        setContactPersonId(_participantId);
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

    public static List<Participant> whereHasCourseId(String courseId) {
        return where("course_id = " + courseId );
    }


}
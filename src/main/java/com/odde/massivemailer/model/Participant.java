package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table("COURSE_CONTACT_PERSON")
public class Participant extends Model {

    private String courseId;
    private String contactPersonId;

    public Map<String, String> attributes = new HashMap<>();

    public Participant() {

    }

    public Participant(String _participantId, String _courseId) {
        this.contactPersonId = _participantId;
        this.courseId = _courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setContactPersonId(String contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public String getContactPersonId() {
        return this.contactPersonId;
    }

    public static List<Participant> whereHasCourseId(String courseId) {
        return where("course_id = " + courseId + " ) ");
    }


}
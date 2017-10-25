package com.odde.massivemailer;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by csd on 1/6/17.
 */
public class CourseDataMother {

    private Course csd_course;
    private ContactPerson contact_alex;

    public Course build_csd_course() {
        Map<String, String> params = new HashMap<>();

        params.put("coursename", "CSD_NEW");
        params.put("location", "SG");
        params.put("address", "roberts lane");
        params.put("coursedetails", "CSD new course details");
        params.put("duration", "5");
        params.put("instructor", "ROOF");
        params.put("startdate", "2017-05-15");

        return new Course(params);
    }

    public Course csd_course() {
        if(csd_course == null) {
            csd_course = build_csd_course();
            csd_course.saveIt();
        }
        return csd_course;
    }

    public ContactPerson contact_alex() {
        if (contact_alex == null) {
            contact_alex = new ContactPerson("John", "john@gmail.com", "Doe", "ComA");
            contact_alex.saveIt();
        }
        return contact_alex;
    }
}

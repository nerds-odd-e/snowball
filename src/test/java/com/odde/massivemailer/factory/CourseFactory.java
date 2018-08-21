package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;

import java.util.HashMap;
import java.util.Map;

import static com.odde.massivemailer.factory.ContactFactory.uniqueContact;

public class CourseFactory {

    private Course csd_course;
    private ContactPerson contact_alex;

    public Course build_csd_course() {
        Map<String, Object> params = new HashMap<>();

        params.put("coursename", "CSD_NEW");
        params.put("country", "singapore");
        params.put("city", "singapore");
        params.put("address", "roberts lane");
        params.put("coursedetails", "CSD new course details");
        params.put("duration", "5");
        params.put("instructor", "ROOF");
        params.put("startdate", "2017-05-15");
        params.put("latitude", 100.0);
        params.put("longitude", 100.0);

        try {
            return new Course().fromMap(params);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
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
            contact_alex = uniqueContact().set("firstname", "alex");
            contact_alex.saveIt();
        }
        return contact_alex;
    }
}

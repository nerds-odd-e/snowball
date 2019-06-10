package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;

import java.util.HashMap;
import java.util.Map;

import static com.odde.massivemailer.factory.ContactFactory.uniqueContact;
import static com.odde.massivemailer.model.base.Repository.repo;

public class CourseFactory {

    private Course csd_course;
    private ContactPerson contact_alex;
    static int uniq_id = 0;

    public static Course uniqueCourse() {
        uniq_id += 1;
        return Course.create();
    }

    public Course build_csd_course() {
        Map<String, Object> params = new HashMap<>();

        params.put("courseName", "CSD_NEW");
        params.put("country", "singapore");
        params.put("city", "singapore");
        params.put("address", "roberts lane");
        params.put("courseDetails", "CSD new course details");
        params.put("duration", "5");
        params.put("instructor", "ROOF");
        params.put("startDate", "2017-05-15");

        try {
            return repo(Course.class).fromMap(params);
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
            contact_alex = uniqueContact();
            contact_alex.setFirstName("alex");
            contact_alex.saveIt();
        }
        return contact_alex;
    }
}

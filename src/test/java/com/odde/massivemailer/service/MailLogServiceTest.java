package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Mail;
import com.sun.jna.platform.win32.Sspi;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class MailLogServiceTest {

    @Test
    public void mailLogMustBeSaved(){
        MailLogService maillogService = new MailLogService();
        int course_id = 1;
        int contact_person_id = 1;
        Date date = new Date();
        assertTrue(maillogService.saveLog(contact_person_id, course_id, date));
    }

    @Test
    public void mailLogsMustBeSaved(){
        MailLogService maillogService = new MailLogService();

        int contact_person_id = 1;
        List<Course> courses = new ArrayList<Course>();

        Course course1 = new Course("testTitle1", "testContent1", "testLocation1");
        course1.setId(1); // without this, NullPointerException
        Course course2 = new Course("testTitle2", "testContent2", "testLocation2");
        course2.setId(2);

        courses.add(course1);
        courses.add(course2);

        assertTrue(maillogService.saveLogs(contact_person_id, courses));
    }


}

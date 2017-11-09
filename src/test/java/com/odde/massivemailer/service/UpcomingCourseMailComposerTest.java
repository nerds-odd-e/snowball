package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Mail;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UpcomingCourseMailComposerTest {
    @Test
    public void WithContactInfo() throws Exception {
        UpcomingCourseMailComposer composer = new UpcomingCourseMailComposer();
        ContactPerson contact = new ContactPerson();
        List<Course> courses = new ArrayList<Course>();
        Mail mail = composer.createUpcomingCourseMail(contact, courses);
        assertEquals(contact.getEmail(), mail.getReceipts().get(0));
    }
}
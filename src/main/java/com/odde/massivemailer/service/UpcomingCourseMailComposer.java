package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Mail;

import java.util.List;
import java.util.stream.Collectors;

public class UpcomingCourseMailComposer {
    public Mail createUpcomingCourseMail(ContactPerson person, List<Course> coursesNearContact) {

        String content = coursesNearContact.stream().map(Course::getCourseName).collect(Collectors.joining("<br/>\n"));
        return Mail.createUpcomingCoursesEmail(content, person.getEmail());
    }
}

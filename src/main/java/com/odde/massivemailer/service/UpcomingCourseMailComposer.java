package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Mail;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UpcomingCourseMailComposer {
    public Mail createUpcomingCourseMail(ContactPerson person, List<Course> coursesNearContact) throws IOException {

        String content = coursesNearContact.stream().map(Course::getCoursename).collect(Collectors.joining("<br/>\n"));
        Mail upcomingCoursesEmail = Mail.createUpcomingCoursesEmail(content, person.getEmail());
        return upcomingCoursesEmail;
    }
}

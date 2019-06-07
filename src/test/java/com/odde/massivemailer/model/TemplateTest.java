package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(TestWithDB.class)
public class TemplateTest {

    private Template template = null;
    private Course course = null;
    private ContactPerson contactPerson = null;
    private Template template2 = null;

    @Before
    public void setUp() {
        template = new Template();
        template.setSubject("Greeting {FirstName}");
        template.setTemplateName("MyDefaultTemplate");
        template.setContent("Hi {FirstName} {LastName}. Please find course details - {CourseName}");

        course = new Course();
        course.setCourseName("CSD");
        course.setInstructor("Roof-TheExpert");
        course.setCity("Singapore");
        course.setCountry("Singapore");

        contactPerson = ContactPerson.repository().fromKeyValuePairs(
                "firstName", "Madhan",
                "email", "Madhan@CS.com",
                "lastName", "Karunakaran",
                "company", "CS",
                "country", "Singapore",
                "city", "Singapore");

        template2 = new Template("DefaultTestTemplate", "Greetings {FirstName}", "Dear {FirstName} {LastName}. Please find the details of the course {CourseName} {Location} {Instructor} ");

    }

    @Test
    public void mustReturnEmptyEmailWhenNOParticipantsInCourse() {

        Assert.assertEquals(0, template.getPopulatedEmailTemplate(course, new ArrayList<>()).size());
    }

    @Test
    public void mustReturnValidEmailWith_1_CourseParticipant() {

        List<Mail> mailList = template.<Mail>getPopulatedEmailTemplate(course, Collections.singletonList(contactPerson));

        Assert.assertNotNull(mailList);
        Assert.assertEquals(1, mailList.size());

        Assert.assertEquals("Greeting Madhan", mailList.get(0).getSubject());
        Assert.assertEquals("Hi Madhan Karunakaran. Please find course details - CSD", mailList.get(0).getContent());

    }

    @Test
    public void mustReturnIncompleteEmailWith_1_CourseParticipant() {
        List<Mail> mailList = template.<Mail>getPopulatedEmailTemplate(course, Collections.singletonList(contactPerson));

        Assert.assertNotNull(mailList);
        Assert.assertEquals(1, mailList.size());

        Assert.assertEquals("Greeting Madhan", mailList.get(0).getSubject());
        Assert.assertEquals("Hi Madhan Karunakaran. Please find course details - CSD", mailList.get(0).getContent());

    }

    @Test
    public void mustContainFirstName() {
        template2 = new Template("DefaultTestTemplate", "Greetings {FirstName}", "Dear {FirstName} {LastName}. Please find the details of the course {CourseName} {Location} {Instructor} ");
        template2.saveIt();

        List<Template> tempList = Template.findByTemplateName("DefaultTestTemplate");

        Assert.assertNotNull(tempList);
        Assert.assertEquals(1, tempList.size());
        //
        Assert.assertTrue(tempList.get(0).getContent().contains("{FirstName}"));
    }


}

package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.controller.TemplatesControllerTest;
import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(TestWithDB.class)
public class TemplateTest {

    @Test
    public void mustReturnEmptyEmailWhenNOParticipantsInCourse() {

        Template template = new Template();
        template.setSubject("Hi {FirstName}");
        template.setTemplateName("MyDefaultTemplate");
        template.setContent("Hi {FirstName} {LastName}. Please ...");

        Course course = new Course();
        course.setCourseName("CSD");

        Assert.assertEquals(0, template.getPopulatedEmailTemplate(course, new ArrayList<ContactPerson>()).size());
    }

    @Test
    public void mustReturnValidEmailWith_1_CourseParticipant() {
        Template template = new Template();
        template.setSubject("Greeting {FirstName}");
        template.setTemplateName("MyDefaultTemplate");
        template.setContent("Hi {FirstName} {LastName}. Please find course details - {CourseName}");

        Course course = new Course();
        course.setCourseName("CSD");
        course.setInstructor("Roof-TheExpert");
        course.setLocation("Singapore-ParkRoyal");


        ContactPerson contactPerson = new ContactPerson("Madhan", "Madhan@CS.com", "Karunakaran", "CS", "Singapore-Changi");
        List<Mail> mailList = template.<Mail>getPopulatedEmailTemplate(course, Collections.<ContactPerson>singletonList(contactPerson));

        Assert.assertNotNull(mailList);
        Assert.assertEquals(1, mailList.size());

        Assert.assertEquals("Greeting Madhan", mailList.get(0).getSubject());
        Assert.assertEquals("Hi Madhan Karunakaran. Please find course details - CSD", mailList.get(0).getContent());

    }

    @Test
    public void mustReturnIncompleteEmailWith_1_CourseParticipant() {
        Template template = new Template();
        template.setSubject("Greeting {FirstName}");
        template.setTemplateName("MyDefaultTemplate");
        template.setContent("Hi {FirstName} {LastName}. Please find course details - {CourseName}");

        Course course = new Course();
        course.setCourseName("CSD");
        course.setInstructor("Roof-TheExpert");
        course.setLocation("Singapore-ParkRoyal");


        ContactPerson contactPerson = new ContactPerson("Madhan", "Madhan@CS.com", "Karunakaran", "CS", "Singapore-Changi");
        List<Mail> mailList = template.<Mail>getPopulatedEmailTemplate(course, Collections.<ContactPerson>singletonList(contactPerson));

        Assert.assertNotNull(mailList);
        Assert.assertEquals(1, mailList.size());

        Assert.assertEquals("Greeting Madhan", mailList.get(0).getSubject());
        Assert.assertEquals("Hi Madhan Karunakaran. Please find course details - CSD", mailList.get(0).getContent());

    }

    @Test
    public void mustContainFirstName() {
        Template template = new Template("DefaultTestTemplate", "Greetings {FirstName}", "Dear {FirstName} {LastName}. Please find the details of the course {CourseName} {Location} {Instructor} ");
        template.saveIt();

        List<Template> tempList = Template.findByTemplateName("DefaultTestTemplate");

        Assert.assertNotNull(tempList);
        Assert.assertEquals(1, tempList.size());
        //
        Assert.assertTrue(tempList.get(0).getString("Content").contains("{FirstName}"));
    }


}

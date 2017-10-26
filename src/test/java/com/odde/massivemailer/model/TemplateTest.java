package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.controller.TemplatesControllerTest;
import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
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
        //
        course = new Course();
        course.setCourseName("CSD");
        course.setInstructor("Roof-TheExpert");
        course.setLocation("18RobertsLane,Singapore");
        //
        contactPerson = new ContactPerson("Madhan", "Madhan@CS.com", "Karunakaran", "CS", "Singapore/Singapore");
        //

        template2 = new Template("DefaultTestTemplate", "Greetings {FirstName}", "Dear {FirstName} {LastName}. Please find the details of the course {CourseName} {Location} {Instructor} ");

    }

    @After
    public void tearDown() {
        template = null;
        course = null;
        contactPerson = null;
        template2.delete();
        template2 = null;
    }

    @Test
    public void mustReturnEmptyEmailWhenNOParticipantsInCourse() {

        Assert.assertEquals(0, template.getPopulatedEmailTemplate(course, new ArrayList<ContactPerson>()).size());
    }

    @Test
    public void mustReturnValidEmailWith_1_CourseParticipant() {

        List<Mail> mailList = template.<Mail>getPopulatedEmailTemplate(course, Collections.<ContactPerson>singletonList(contactPerson));

        Assert.assertNotNull(mailList);
        Assert.assertEquals(1, mailList.size());

        Assert.assertEquals("Greeting Madhan", mailList.get(0).getSubject());
        Assert.assertEquals("Hi Madhan Karunakaran. Please find course details - CSD", mailList.get(0).getContent());

    }

    @Test
    public void mustReturnIncompleteEmailWith_1_CourseParticipant() {
        List<Mail> mailList = template.<Mail>getPopulatedEmailTemplate(course, Collections.<ContactPerson>singletonList(contactPerson));

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
        Assert.assertTrue(tempList.get(0).getString("Content").contains("{FirstName}"));
    }


}

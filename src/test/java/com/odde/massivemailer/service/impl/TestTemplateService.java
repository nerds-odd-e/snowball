package com.odde.massivemailer.service.impl;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Template;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by csd on 30/5/17.
 */
@RunWith(TestWithDB.class)
public class TestTemplateService {

    private Template template;

    @Before
    public void setup() {
        template = new Template("DefaultTestTemplate", "Greetings {FirstName}", "Dear {FirstName} {LastName}. Please find the details of the course {CourseName} {Location} {Instructor} ");
        template.save();

        try {
            List<Template> tempList = Template.findByTemplateName("DefaultTestTemplate");
            template = tempList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void templateMustNotBeNull() {

        Assert.assertNotNull(template);

    }




    @Test
    public void templateMustContainFirstName() {
        Assert.assertTrue(template.getContent().contains("{FirstName}"));
    }

    @Test
    public void templateMustContainLasttName() {
        Assert.assertTrue(template.getContent().contains("{LastName}"));
    }

    @Test
    public void templateMustContainCourse() {
        Assert.assertTrue(template.getContent().contains("{CourseName}"));
}


    @Test
    public void templateMustContainInstructor() {
        Assert.assertTrue(template.getContent().contains("{Instructor}"));
    }

    @Test
    public void templateMustContainLocation() {
        Assert.assertTrue(template.getContent().contains("{Location}"));
    }

}



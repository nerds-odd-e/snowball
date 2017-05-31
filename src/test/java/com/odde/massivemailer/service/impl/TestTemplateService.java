package com.odde.massivemailer.service.impl;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.service.TemplateService;
import com.odde.massivemailer.startup.DBMigrater;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by csd on 30/5/17.
 */
@RunWith(TestWithDB.class)
public class TestTemplateService {

    TemplateService templateService;
    Template template;

    @BeforeClass
    public static void updateTheDBWithSampleTemplate() {
        //DBMigrater migrater = new DBMigrater();
        //migrater.migrate();

    }

    @Before
    public void setup() {
        template = new Template();
        templateService = new TemplateService();

        try {
            List<Template> tempList = Template.findByTemplateName("Default Template 1");
            //template = templateService.getDefaultTemplate();
            template = tempList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void templateMustNotBeNull() throws Exception {

        Template templateActual = null;
        //templateActual = templateService.getDefaultTemplate();
        Assert.assertNotNull(template);

    }




    @Test
    public void templateMustContainFirstName() throws Exception
    {
        //Template template = new Template("DefaultTestTemplate", "Greetings {FirstName}", "Dear {FirstName} {LastName}. Please find the details of the course {CourseName} {Location} {Instructor} ");
        //template.saveIt();

        //List<Template> tempList = Template.findByTemplateName("DefaultTestTemplate");
        //Template templateActual = templateService.getDefaultTemplate("DefaultTestTemplate");
        //templateActual = (String) tempList.get(0).get("content");


        Assert.assertTrue(template.getString("Content").contains("{FirstName}"));
    }

    @Test
    public void templateMustContainLasttName() throws Exception
    {
        Assert.assertTrue(template.getString("Content").contains("{LastName}"));
    }

    @Test
    public void templateMustContainCourse() throws Exception
    {
        Assert.assertTrue(template.getString("Content").contains("{CourseName}"));
}


    @Test
    public void templateMustContainInstructor() throws Exception
    {
        Assert.assertTrue(template.getString("Content").contains("{Instructor}"));
    }

    @Test
    public void templateMustContainLocation() throws Exception
    {
        Assert.assertTrue(template.getString("Content").contains("{Location}"));
    }

    @Test
    public void populateTemplateMustReturnNull() throws Exception
    {
      //  templateService.populateTemplate(null, )
    }

}



package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.service.TemplateService;
import com.odde.massivemailer.startup.DBMigrater;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by csd on 30/5/17.
 */
public class TestTemplateService {

    TemplateService templateService;
    String template;

    @BeforeClass
    public static void updateTheDBWithSampleTemplate() {
        //DBMigrater migrater = new DBMigrater();
        //migrater.migrate();
    }

    @Before
    public void setup() {
        templateService = new TemplateService();

        try {
            template = templateService.getDefaultTemplate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void templateMustNotBeNull() throws Exception {

        String templateActual = null;
        templateActual = templateService.getDefaultTemplate();
        Assert.assertNotNull(templateActual);

    }



    public void templateMustMatchDBValue() {
        String dbTemplate = "Hi, {FirstName} {LastName} from {Company}";
        String templateActual = null;
        try {
            templateActual = templateService.getDefaultTemplate();
        }
        catch (Exception e)
        {
            Assert.fail();
        }
        Assert.assertEquals("Template retrieved by service is different from database", dbTemplate, templateActual);
    }


    @Test
    public void templateMustContainFirstName() throws Exception
    {
         Assert.assertTrue(template.contains("{FirstName}"));
    }

    @Test
    public void templateMustContainLasttName() throws Exception
    {
        Assert.assertTrue(template.contains("{LastName}"));
    }

    //@Test
    public void templateMustContainCourse() throws Exception
    {
        Assert.assertTrue(template.contains("{CourseName}"));
    }

    //@Test
    public void templateMustContainDuration() throws Exception
    {
        Assert.assertTrue(template.contains("{Duration}"));
    }

    //@Test
    public void templateMustContainStartDate() throws Exception
    {
        Assert.assertTrue(template.contains("{StartDate}"));
    }

    //@Test
    public void templateMustContainEndDate() throws Exception
    {
        Assert.assertTrue(template.contains("{EndDate}"));
    }

    //@Test
    public void templateMustContainInstructor() throws Exception
    {
        Assert.assertTrue(template.contains("{Instructor}"));
    }

    //@Test
    public void templateMustContainLocation() throws Exception
    {
        Assert.assertTrue(template.contains("{Location}"));
    }

}



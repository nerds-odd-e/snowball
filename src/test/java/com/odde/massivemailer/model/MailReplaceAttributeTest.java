package com.odde.massivemailer.model;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MailReplaceAttributeTest {


    ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe", "Apple");

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"Greeting John",      "Greeting {FirstName}"},
                {"Greeting Doe",      "Greeting {LastName}"},
                {"Greeting Apple",     "Greeting {Company}"}
        });
    }

    @Parameterized.Parameter
    public String expected;
    @Parameterized.Parameter (value = 1)
    public String template;

    @org.junit.Test
    public void testTemplate() {
        assertEquals(expected,  new Mail().ReplaceAttibuteValue(template, contact));
    }

}

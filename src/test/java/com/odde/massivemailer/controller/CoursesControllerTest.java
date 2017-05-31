package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Course;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by csd on 30/5/17.
 */
@RunWith(TestWithDB.class)
public class CoursesControllerTest {

    @Test
    public void testCourse() {
        Course course = new Course.CourseBuilder().setStartdate("2017-05-07").setLocation("singapore").setInstructor("Terry").setDuration("12").setCoursename("csd").setAddress("Roberts lane").setCoursedetails("csd scrum developer").build();

        Assert.assertEquals("singapore", course.getLocation());
    }

    /*@Test
    public void testSaveCourse() {
        Course course = new Course.CourseBuilder().setStartdate("2017-05-07").setLocation("singapore1").setInstructor("Terry1").setDuration("15").setCoursename("csd").setAddress("Roberts lane").setCoursedetails("csd scrum developer").build();

        course.saveIt();

        Assert.assertNotNull(course.getId());
    }*/
}

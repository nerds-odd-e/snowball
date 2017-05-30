package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Course;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * Created by csd on 30/5/17.
 */
@RunWith(TestWithDB.class)
public class CoursesControllerTest {

    @Test
    public void testCourse() {
        Course course = new Course.CourseBuilder().setStartdate(new Date()).setLocation("singapore").setInstructor("Terry").setDuration("5 days").setCoursename("csd").setAddress("Roberts lane").setCoursedetails("csd scrum developer").build();

        Assert.assertEquals("singapore", course.getLocation());


    }
}

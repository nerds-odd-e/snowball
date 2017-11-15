package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class CourseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private HashMap map;

    @Before
    public void Setup(){
        map = new HashMap<String, Object>();
        map.put("coursename", "coursename");
        map.put("country", "country");
        map.put("city", "city");
        map.put("address", "address");
        map.put("coursedetails", "coursedetails");
        map.put("duration", "15");
        map.put("instructor", "instructor");
        map.put("startdate", "startdate");
    }

    @Test
    public void testCreateCourseShouldHaveCorrectInformation() throws Exception {

        Course course = new Course(map);

        assertEquals("coursename", course.getCoursename());
        assertEquals("country/city", course.getLocation());
        assertEquals("address", course.getAddress());
        assertEquals("coursedetails", course.getCoursedetails());
        assertEquals("15", course.getDuration());
        assertEquals("instructor", course.getInstructor());
        assertEquals("startdate", course.getStartdate());
    }

    @Test
    public void testGenerateLatLongFromSingaporeShouldReturnSuccess() throws Exception {

        map.put("country", "Singapore");
        map.put("city", "Singapore");

        Course course = new Course(map);

        assertEquals(1.3521, course.getLatitude(), 0.0001);
        assertEquals(103.8198, course.getLongitude(), 0.0001);
    }
}

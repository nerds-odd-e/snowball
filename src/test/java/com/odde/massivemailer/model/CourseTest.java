package com.odde.massivemailer.model;
import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class CourseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private HashMap map;
    private static Course aCourse;
    private static Course sgCourse;
    private static Course klCourse;
    private static Course usaCourse;

    @Before
    public void Setup()throws Exception{
        map = new HashMap<String, Object>();
        map.put("coursename", "Scrum");
        map.put("country", "country");
        map.put("city", "city");
        map.put("address", "address");
        map.put("coursedetails", "coursedetails");
        map.put("duration", "15");
        map.put("instructor", "instructor");
        map.put("startdate", "2017-11-09");
        aCourse = new Course().fromMap(map);
        GivenIhaveThreeCourcesInSG_Kl_USA();
    }

    @Test
    public void testCreateCourseShouldHaveCorrectInformation() throws Exception {

        assertEquals("Scrum", aCourse.getCoursename());
        assertEquals("country/city", aCourse.getLocation());
        assertEquals("address", aCourse.getAddress());
        assertEquals("coursedetails", aCourse.getCoursedetails());
        assertEquals("15", aCourse.getDuration());
        assertEquals("instructor", aCourse.getInstructor());
        assertEquals("2017-11-09", aCourse.getStartdate());
    }

    @Test
    public void whereNearTo_should_return_emptyList() {

        Location unknownGeolocation =  new Location("unknown/unknown",-999999999.0,-9999999.0);

        assertEquals(0, Course.findAllCourseNearTo(unknownGeolocation).size());
    }

    @Test
    public void whereNearTo_should_return_courses_In_America_When_America_Location_is_given() {

        Location usa = new Location(usaCourse.getLocation(),40.712775,-74.005973);

        List<Course> courses = Course.findAllCourseNearTo(usa);
        assertEquals(1, courses.size());
        assertEquals(usaCourse, courses.get(0));
    }

    @Test
    public void whereNearTo_should_return_courses_In_SG_And_MY_When_SG_Location_is_given() {

        Location sg = new Location(sgCourse.getLocation(),1.352083,103.819836);

        List<Course> courses = Course.findAllCourseNearTo(sg);
        assertEquals(2, courses.size());
        assertEquals(sgCourse, courses.get(0));
        assertEquals(klCourse, courses.get(1));
    }

    private void GivenIhaveThreeCourcesInSG_Kl_USA() throws Exception {
        sgCourse = CreateCourseInDB("Singapore","Singapore", "Stanly");
        klCourse = CreateCourseInDB("Kula Lumpur","Malaysia", "Terry");
        usaCourse = CreateCourseInDB("New York","America", "Kim");
    }

    private Course CreateCourseInDB(String country, String city, String instructor) throws Exception {
        map.put("coursename", "Scrum");
        map.put("country", country);
        map.put("city", city);
        map.put("instructor", instructor);
        map.put("coursedetails", "This is a great course");
        Course course = new Course().fromMap(map);
        course.saveIt();
        return course;
    }

}


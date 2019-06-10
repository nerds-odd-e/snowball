package com.odde.massivemailer.model;
import com.odde.TestWithDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static com.odde.massivemailer.model.base.Repository.repo;
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
    public void Setup() {
        map = new HashMap<String, Object>();
        map.put("courseName", "Scrum");
        map.put("country", "Japan");
        map.put("city", "Tokyo");
        map.put("address", "address");
        map.put("courseDetails", "courseDetails");
        map.put("duration", "15");
        map.put("instructor", "instructor");
        map.put("startDate", "2017-11-09");
        aCourse = repo(Course.class).fromMap(map).saveIt();
        GivenIhaveThreeCourcesInSG_Kl_USA();
    }

    @Test
    public void testCreateCourseShouldHaveCorrectInformation() {

        assertEquals("Scrum", aCourse.getCourseName());
        assertEquals("Japan/Tokyo", aCourse.location());
        assertEquals("address", aCourse.getAddress());
        assertEquals("courseDetails", aCourse.getCourseDetails());
        assertEquals("15", aCourse.getDuration());
        assertEquals("instructor", aCourse.getInstructor());
        assertEquals("2017-11-09", aCourse.getStartDate());
    }

    @Test
    public void whereNearTo_should_return_emptyList() {

        Location unknownGeolocation =  new Location("unknown/unknown",-999999999.0,-9999999.0);

        assertEquals(0, Course.findAllCourseNearTo(unknownGeolocation).size());
    }

    @Test
    public void whereNearTo_should_return_courses_In_America_When_America_Location_is_given() {

        Location usa = new Location(usaCourse.location(),40.712775,-74.005973);

        List<Course> courses = Course.findAllCourseNearTo(usa);
        assertEquals(1, courses.size());
        assertEquals(usaCourse, courses.get(0));
    }

    @Test
    public void whereNearTo_should_return_courses_In_SG_And_MY_When_SG_Location_is_given() {

        Location sg = new Location(sgCourse.location(),1.352083,103.819836);

        List<Course> courses = Course.findAllCourseNearTo(sg);
        assertEquals(2, courses.size());
        assertEquals(sgCourse, courses.get(0));
        assertEquals(klCourse, courses.get(1));
    }

    private void GivenIhaveThreeCourcesInSG_Kl_USA() {
        sgCourse = CreateCourseInDB("Singapore","Singapore", "Stanly");
        klCourse = CreateCourseInDB("Malaysia", "Kuala Lumpur", "Terry");
        usaCourse = CreateCourseInDB("USA", "New York", "Kim");
    }

    private Course CreateCourseInDB(String country, String city, String instructor) {
        map.put("courseName", "Scrum");
        map.put("country", country);
        map.put("city", city);
        map.put("instructor", instructor);
        map.put("courseDetails", "This is a great course");
        Course course = repo(Course.class).fromMap(map);
        course.saveIt();
        return course;
    }

}


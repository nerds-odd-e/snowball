package com.odde.snowball.controller;

import com.google.gson.internal.LinkedTreeMap;
import com.odde.TestWithDB;
import com.odde.snowball.model.*;
import com.odde.snowball.serialiser.AppGson;
import com.odde.snowball.service.LocationProviderService;
import com.odde.snowball.service.exception.GeoServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(TestWithDB.class)
public class CoursesControllerTest {
    private final CoursesController controller = new CoursesController();
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Before
    public void setUp() {
        LocationProviderService.resetCachedLocations();
    }

    @Test
    public void shouldCreateACourseWithOnlyCourseName() throws IOException {
        request.setParameter("courseName", "test couree");
        controller.doPost(request, response);
        assertEquals("add_course.jsp?status=success&msg=Add course successfully",response.getRedirectedUrl());
        assertEquals("test couree", Course.getCourseByName("test couree").getCourseName());
    }

    @Test
    public void shouldCreateACourseWithCourseName_Location() throws IOException, GeoServiceException {
        request.setParameter("courseName", "test couree");
        request.setParameter("country", "Japan");
        request.setParameter("city", "Osaka");
        controller.doPost(request, response);

        assertEquals("Japan/Osaka", Course.getCourseByName("test couree").location());

        LocationProviderService service = new LocationProviderService();
        Location storedLocation = service.getLocationForName("Japan/Osaka");
        assertNotNull(storedLocation);
    }

    @Test
    public void shouldNotCreateACourseWithWrongLocationInformation() throws IOException {
        request.setParameter("courseName", "test couree");
        request.setParameter("country", "FooBar");
        request.setParameter("city", "FooBarXXX");
        controller.doPost(request, response);
        assertNull(Course.getCourseByName("test couree"));
        assertEquals("add_course.jsp?status=fail&msg={ geoLocation.latitude:\"This location cannot be found\" }",response.getRedirectedUrl());
    }

    @Test
    public void mustNotContainAnyCourseWhenCurrentUserIsNotAContact() throws IOException {
        new User("non_contact@gmail.com").save();
        createCourse("Bob's course");
        Cookie sessionCookie = new Cookie("session_id", "non_contact@gmail.com");
        request.setCookies(new Cookie[]{sessionCookie});
        controller.doGet(request, response);
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    public void mustNotContainCourseDoseNotBelongToCurrentUser() throws IOException {
        ContactPerson mary = createContactPersonWithUserAccount("mary@example.com");
        createCourse("Bob's course");

        Cookie sessionCookie = new Cookie("session_id", mary.getEmail());
        request.setCookies(new Cookie[]{sessionCookie});
        controller.doGet(request, response);
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    public void mustContainAllCourseWithoutEmail() throws IOException {
        Course bobsCourse = createCourse("Bob's course");
        Course anotherCourse = createCourse("anotherCourse");

        controller.doGet(request, response);
        List<LinkedTreeMap<String, Object>> courses = AppGson.getGson().fromJson(response.getContentAsString(), List.class);
        assertEquals(2, courses.size());
        Map attributes = courses.get(0);
        assertEquals(bobsCourse.getCourseName(), attributes.get("courseName"));
        attributes = courses.get(1);
        assertEquals(anotherCourse.getCourseName(), attributes.get("courseName"));
    }

    @Test
    public void mustContainCourseBelongToCurrentUser() throws IOException {
        ContactPerson bob = createContactPersonWithUserAccount("bob@example.com");
        Course bobsCourse = createCourse("Bob's course");
        createCourse("anotherCourse");

        Participant participant = new Participant(bob.getId(), bobsCourse.getId());
        participant.save();

        Cookie otherCookie = new Cookie("any", "any");
        Cookie sessionCookie = new Cookie("session_id", bob.getEmail());
        request.setCookies(new Cookie[]{otherCookie, sessionCookie});
        controller.doGet(request, response);
        List<LinkedTreeMap<String, Object>> courses = AppGson.getGson().fromJson(response.getContentAsString(), List.class);
        assertEquals(1, courses.size());
        Map attributes = courses.get(0);
        assertEquals(bobsCourse.getCourseName(), attributes.get("courseName"));
    }

    private Course createCourse(String courseName) {
        HashMap<String, Object> courseValue = new HashMap<>();
        courseValue.put("courseDetails", "detail");
        courseValue.put("city", "Tokyo");
        courseValue.put("country", "Japan");

        Course course = repo(Course.class).fromMap(courseValue);
        course.setCourseName(courseName);
        course.save();
        return course;
    }

    private ContactPerson createContactPersonWithUserAccount(String email) {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setEmail(email);
        contactPerson.save();
        new User(email).save();
        return contactPerson;
    }
}

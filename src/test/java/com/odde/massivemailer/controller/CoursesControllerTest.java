package com.odde.massivemailer.controller;

import com.google.gson.internal.LinkedTreeMap;
import com.odde.TestWithDB;
import com.odde.massivemailer.model.*;
import com.odde.massivemailer.serialiser.AppGson;
import com.odde.massivemailer.service.LocationProviderService;
import com.odde.massivemailer.service.exception.GeoServiceException;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(TestWithDB.class)
public class CoursesControllerTest {
    private CoursesController controller = new CoursesController();
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();

    @Before
    public void setUp() {
        LocationProviderService.resetLocations();
    }

    @Test
    public void shouldCreateACourseWithOnlyCourseName() throws IOException {
        request.setParameter("coursename", "test couree");
        controller.doPost(request, response);
        assertEquals("add_course.jsp?status=success&msg=Add course successfully",response.getRedirectedUrl());
        assertEquals("test couree", Course.getCourseByName("test couree").getCoursename());
    }

    @Test
    public void shouldCreateACourseWithCourseName_Location() throws IOException, GeoServiceException {
        request.setParameter("coursename", "test couree");
        request.setParameter("country", "Japan");
        request.setParameter("city", "Osaka");
        controller.doPost(request, response);

        assertEquals("Japan/Osaka", Course.getCourseByName("test couree").getLocation());

        LocationProviderService service = new LocationProviderService();
        Location storedLocation = service.getLocationForName("Japan/Osaka");
        assertEquals("Japan/Osaka", storedLocation.getName());
    }

    @Test
    public void shouldNotCreateACourseWithWrongLocationInformation() throws IOException, GeoServiceException {
        request.setParameter("coursename", "test couree");
        request.setParameter("country", "FooBar");
        request.setParameter("city", "FooBarXXX");
        controller.doPost(request, response);
        assertNull(Course.getCourseByName("test couree"));
        assertEquals("add_course.jsp?status=fail&msg={ city:\"cannot be located\" }",response.getRedirectedUrl());
    }

    @Test
    public void mustNotContainAnyCourseWhenCurrentUserIsNotAContact() throws IOException {
        new User("non_contact@gmail.com").saveIt();
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
        Map attributes = (Map) courses.get(0).get("attributes");
        assertEquals(bobsCourse.getCoursename(), attributes.get("coursename"));
        attributes = (Map) courses.get(1).get("attributes");
        assertEquals(anotherCourse.getCoursename(), attributes.get("coursename"));
    }

    @Test
    public void mustContainCourseBelongToCurrentUser() throws IOException {
        ContactPerson bob = createContactPersonWithUserAccount("bob@example.com");
        Course bobsCourse = createCourse("Bob's course");
        createCourse("anotherCourse");

        Participant participant = new Participant(Integer.parseInt(bob.getId().toString()), Integer.parseInt(bobsCourse.getId().toString()));
        participant.saveIt();

        Cookie otherCookie = new Cookie("any", "any");
        Cookie sessionCookie = new Cookie("session_id", bob.getEmail());
        request.setCookies(new Cookie[]{otherCookie, sessionCookie});
        controller.doGet(request, response);
        List<LinkedTreeMap<String, Object>> courses = AppGson.getGson().fromJson(response.getContentAsString(), List.class);
        assertEquals(1, courses.size());
        Map attributes = (Map) courses.get(0).get("attributes");
        assertEquals(bobsCourse.getCoursename(), attributes.get("coursename"));
    }

    private Course createCourse(String courseName) {
        HashMap<String, Object> courseValue = new HashMap<>();
        courseValue.put("coursedetails", "detail");
        courseValue.put("city", "Tokyo");
        courseValue.put("country", "Japan");

        Course course = new Course().fromMap(courseValue);
        course.setCourseName(courseName);
        course.saveIt();
        return course;
    }

    private ContactPerson createContactPersonWithUserAccount(String email) {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setEmail(email);
        contactPerson.saveIt();
        new User(email).saveIt();
        return contactPerson;
    }
}

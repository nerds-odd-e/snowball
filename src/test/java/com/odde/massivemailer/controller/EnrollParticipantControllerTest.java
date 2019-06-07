package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.odde.massivemailer.factory.CourseFactory.uniqueCourse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class EnrollParticipantControllerTest {

    private EnrollParticipantController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new EnrollParticipantController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void saveParticipantInCourse() {
        Course course = uniqueCourse();
        ObjectId id = new ObjectId();
        course.setId(id);
        request.setParameter("courseId", id.toString());
        request.setParameter("participants", "tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore");
        controller.doPost(request, response);

        List<ContactPerson> participants = course.participants();
        ContactPerson contactByEmail = ContactPerson.getContactByEmail("tom@example.com");

        assertEquals(1, participants.size());
        assertEquals("tom@example.com", contactByEmail.getEmail());
    }

    @Test
    public void saveParticipantsInCourse() {
        Course course = uniqueCourse();
        ObjectId objectId = new ObjectId();
        course.setId(objectId);
        request.setParameter("courseId", objectId.toString());
        String inputTsvLines = Stream.of(
                "tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore",
                "carry@example.com\tCarry\tTrek\tCS\tSingapore\tSingapore"
        ).collect(Collectors.joining("\n"));
        request.setParameter("participants", inputTsvLines);
        controller.doPost(request, response);

        List<ContactPerson> participants = course.participants();
        ContactPerson tom = ContactPerson.getContactByEmail("tom@example.com");
        ContactPerson carry = ContactPerson.getContactByEmail("carry@example.com");

        assertThat(response.getRedirectedUrl()).isEqualTo("course_detail.jsp?id="+objectId.toString()+"&errors=");
        assertEquals(2, participants.size());
        assertEquals("tom@example.com", tom.getEmail());
        assertEquals("carry@example.com", carry.getEmail());
    }

    @Test
    public void saveParticipantInCourseWithError() throws Throwable {
        String takamiyaEmail = "takemiya@  Keisuke  Smith  CS  Singapore  Singapore";
        String odaEmail = "odashota.com  Keisuke  Smith  CS  Singapore  Singapore";
        request.setParameter("courseId", "123");
        String testLine = takamiyaEmail + "\n" + odaEmail;
        request.setParameter("participants", testLine);
        controller.doPost(request, response);

        assertEquals("course_detail.jsp?id=123&errors=" + URLEncoder.encode(testLine, "UTF-8"), response.getRedirectedUrl());
    }
}

package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.LocationProviderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by csd on 30/5/17.
 */
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
    public void shouldCreateACourseWithOnlyCourseName() throws ServletException, IOException {
        request.setParameter("coursename", "test couree");
        controller.doPost(request, response);
        Assert.assertEquals("add_course.jsp?status=success&msg=Add course successfully",response.getRedirectedUrl());
        Assert.assertEquals("test couree", Course.getCourseByName("test couree").getCoursename());
    }

    @Test
    public void shouldCreateACourseWithCourseName_Location() throws ServletException, IOException {
        request.setParameter("coursename", "test couree");
        request.setParameter("country", "Japan");
        request.setParameter("city", "Osaka");
        controller.doPost(request, response);

        Assert.assertEquals("Japan/Osaka", Course.getCourseByName("test couree").getLocation());

        LocationProviderService service = new LocationProviderService();
        Location storedLocation = service.getLocationForName("Japan/Osaka");
        assertEquals("Japan", storedLocation.getCountryName());
        assertEquals("Osaka", storedLocation.getName());
    }
}

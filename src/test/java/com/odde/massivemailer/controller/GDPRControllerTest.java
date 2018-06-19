package com.odde.massivemailer.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GDPRControllerTest {

    private GDPRController gdprController = new GDPRController();
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();

    @Test
    public void should_return_respose_with_status_code_200 () throws ServletException, IOException {
        gdprController.doPost(req, res);
        assertEquals(200, res.getStatus());
    }

    @Test
    public void should_dispatch_to_gdpr_page () throws ServletException, IOException {
        gdprController.doGet(req, res);
        assertEquals("gdpr.jsp", res.getForwardedUrl());
    }
}

package com.odde.massivemailer.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

public class SampleController extends AppController {

    public void doGet(MockHttpServletRequest req, MockHttpServletResponse res) throws IOException {
        jsonResponse(res, "response!");
    }

    public void doPost(MockHttpServletRequest req, MockHttpServletResponse res) throws IOException {
        jsonResponse(res, "response!");
    }
}

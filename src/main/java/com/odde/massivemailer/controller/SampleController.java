package com.odde.massivemailer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SampleController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        jsonResponse(res, "response!");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        jsonResponse(res, "response!");
    }
}

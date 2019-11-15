package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/launchRetraining")
public class LaunchRetrainingController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/onlinetest/retraining.jsp");
    }
}

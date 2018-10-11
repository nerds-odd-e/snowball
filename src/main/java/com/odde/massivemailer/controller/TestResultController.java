package com.odde.massivemailer.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testresult")
public class TestResultController extends AppController{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        response.sendRedirect("test_result.jsp");
    }
}

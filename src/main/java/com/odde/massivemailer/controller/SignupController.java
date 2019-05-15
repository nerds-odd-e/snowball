package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupController extends AppController {
    public void doPost(HttpServletResponse response) throws IOException {
        String redirectUrl = "tokkun.jsp";
        response.sendRedirect(redirectUrl);
    }
}

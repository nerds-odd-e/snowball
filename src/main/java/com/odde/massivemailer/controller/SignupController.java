package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signup")
public class SignupController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectUrl = "tokkun/top";
        String userName = req.getParameter("userName");
        HttpSession session = req.getSession(true);
        session.setAttribute("userName", userName);
        resp.sendRedirect(redirectUrl);
    }
}

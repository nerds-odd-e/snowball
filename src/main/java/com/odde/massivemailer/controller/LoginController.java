package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = User.getUserByEmail(req.getParameter("email"));
        String url;
        if (user == null) {
            url = "login.jsp";
        } else {
            url = "course_list.jsp";
        }
        resp.sendRedirect(url);
    }
}

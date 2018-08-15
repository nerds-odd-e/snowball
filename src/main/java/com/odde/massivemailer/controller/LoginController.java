package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = User.getUserByEmail(req.getParameter("email"));
        String password = req.getParameter("password");
        String url;

        if (user != null && user.isPasswordCorrect(password)){
            url = "course_list.jsp";
        } else {
            url = "login.jsp?status=fail";
        }
        resp.sendRedirect(url);
    }

}

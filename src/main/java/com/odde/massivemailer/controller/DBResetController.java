package com.odde.massivemailer.controller;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reset")
public class DBResetController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext application = getServletConfig().getServletContext();
        setTestEnv(application);
        cleanDB();
        resp.getWriter().write("db is reset");
    }

    private void setTestEnv(ServletContext application) {
        application.setAttribute("email_sender", "mock");
    }

    private void cleanDB() {
        Base.exec("DELETE FROM Template");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");
        Base.exec("DELETE FROM notification_details");
        Base.exec("DELETE FROM notifications");
        Base.exec("DELETE FROM events");
        Base.exec("DELETE FROM contact_people");
        Base.exec("DELETE FROM courses");
    }
}

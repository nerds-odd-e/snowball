package com.odde.massivemailer.controller;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DBResetController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext application = getServletConfig().getServletContext();
        application.setAttribute("dbLink", "jdbc:sqlite:testdb.db");
        application.setAttribute("email_sender", "mock");
        Base.commitTransaction();
        Base.close();
        Base.open("org.sqlite.JDBC", (String) application.getAttribute("dbLink"), "", "");
        Base.openTransaction();
        Base.exec("DELETE FROM mail;");
        Base.exec("drop table if exists Template;CREATE TABLE Template (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TemplateName VARCHAR(255) NOT NULL, Subject VARCHAR(255), Content NVARCHAR(5000))");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");
        Base.exec("DELETE FROM notification_details");
        Base.exec("DELETE FROM notifications");
        Base.exec("DELETE FROM event");
        resp.getWriter().write("db is reset");

    }
}

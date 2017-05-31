package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.serialiser.AppGson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/templates")
public class TemplatesController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String templateId = req.getParameter("templateList");
        String resultMsg = "status=fail&msg=Update template failure";

        Template updateTemplate = Template.findById(templateId);
        if (templateId != null &&  updateTemplate != null) {
            updateTemplate.setSubject(req.getParameter("subject"));
            updateTemplate.setContent(req.getParameter("content"));
            updateTemplate.saveIt();
            resultMsg = "status=success&msg=Update template successfully";
        }
        resp.sendRedirect("sendemail.jsp?" + resultMsg);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String convertedContactToJSON = AppGson.getGson().toJson(Template.findAll());
        resp.getWriter().write(convertedContactToJSON);
    }
}

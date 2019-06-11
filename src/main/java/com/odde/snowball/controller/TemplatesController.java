package com.odde.snowball.controller;

import com.odde.snowball.model.Template;
import com.odde.snowball.serialiser.AppGson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/templates")
public class TemplatesController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String templateId = req.getParameter("templateList");
        String resultMsg = "status=fail&msg=Update template failure";

        Template updateTemplate = repo(Template.class).findByStringId(templateId);
        if (templateId != null &&  updateTemplate != null) {
            String subject = req.getParameter("subject");
            String content = req.getParameter("content");
            updateTemplate.setSubject(subject);
            updateTemplate.setContent(content);
            updateTemplate.save();
            resultMsg = "status=success&msg=Update template successfully";
        }
        resp.sendRedirect("/admin/sendemail.jsp?" + resultMsg);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String convertedContactToJSON = AppGson.getGson().toJson(repo(Template.class).findAll());
        resp.getWriter().write(convertedContactToJSON);
    }
}

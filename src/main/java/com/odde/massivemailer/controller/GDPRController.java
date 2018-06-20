package com.odde.massivemailer.controller;

import com.odde.massivemailer.service.GDPRService;
import com.odde.massivemailer.service.TemplateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/gdpr")
public class GDPRController extends AppController {
    private static final long serialVersionUID = 1L;

    private TemplateService templateService = new TemplateService();
    private GDPRService gdprService = new GDPRService(getMailService(), templateService);

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        gdprService.sendConsentRequestEmail();
        showSuccess(resp);
    }

   /* public void showFail(HttpServletResponse resp) throws ServletException, IOException  {
        resp.sendError(503, "Fail to send email");
    }*/

    public void showSuccess(HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"result\":\"success\"}");
    }


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("gdpr.jsp").forward(req, resp);
    }
}

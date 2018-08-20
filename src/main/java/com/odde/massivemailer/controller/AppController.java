package com.odde.massivemailer.controller;

import com.odde.massivemailer.serialiser.AppGson;
import com.odde.massivemailer.service.MailService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AppController extends HttpServlet {

    private MailService mailService;

    void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    MailService getMailService() {
        if (mailService == null) {
            mailService = MailService.createMailService();
        }
        return mailService;
    }

    HashMap<String, String> getParameterFromRequest(HttpServletRequest req, String... reqFields) {
        HashMap<String, String> map = new HashMap<>();
        for (String field : reqFields)
            map.put(field, req.getParameter(field));
        return map;
    }

    protected void respondWithJSON(HttpServletResponse resp, Object all) throws IOException {
        String convertedContactToJSON = AppGson.getGson().toJson(all);
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertedContactToJSON);
    }
}

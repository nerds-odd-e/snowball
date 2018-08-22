package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;
import com.odde.massivemailer.serialiser.AppGson;
import com.odde.massivemailer.service.MailService;
import org.apache.commons.lang3.ArrayUtils;
import org.javalite.activejdbc.Errors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

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

    protected void respondWithRedirectAndErrorMessage(HttpServletResponse resp, String page, String message) throws IOException {
        resp.sendRedirect(page +"?" + "status=fail&msg="+ message);
    }

    protected void respondWithRedirectAndSuccessMessage(HttpServletResponse resp, String page, String message) throws IOException {
        resp.sendRedirect(page +"?" + "status=success&msg="+ message);
    }

    protected void respondWithRedirectAndError(HttpServletResponse resp, String page, Errors errors) throws IOException {
        respondWithRedirectAndErrorMessage(resp, page,
                errors.toString().replaceAll("\\=\\<", ":\"").replaceAll("\\>", "\""));
    }

    protected User getCurrentUser(HttpServletRequest request) {
        final String email = getUserEmailFromCookie(request);
        return User.first("email=?", email);
    }

    private String getUserEmailFromCookie(HttpServletRequest request) {
        final String[] email = new String[1];

        Cookie[] cookies = request.getCookies();
        if (!ArrayUtils.isEmpty(cookies)) {
            Optional<Cookie> sessionCookie = Stream.of(cookies).filter(cookie -> "session_id".equals(cookie.getName())).findFirst();
            sessionCookie.ifPresent(cookie -> {
                email[0] = cookie.getValue();
            });
        }
        return email[0];
    }
}

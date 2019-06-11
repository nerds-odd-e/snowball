package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class AppController extends HttpServlet {

    public Map getRequestBody(HttpServletRequest req) throws IOException {
        String result = req.getReader().lines().collect(Collectors.joining("\r\n"));
        return new Gson().fromJson(result, Map.class);
    }

    public void jsonResponse(HttpServletResponse resp, String s) throws IOException {
        resp.setHeader("Content-Type", "application/json; charset=UTF-8");
        resp.getOutputStream().print(s);
    }

    public String toJson(LazyList<Model> objects) {
        String json = String.join(",", objects.stream().map(obj -> obj.toJson(true)).collect(Collectors.toList()));
        return "[" + json + "]";
    }


}

package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.service.LocationProviderService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/countries")
public class CountryController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        respondWithJSON(resp, new LocationProviderService().getAllCountryNames());
    }
}

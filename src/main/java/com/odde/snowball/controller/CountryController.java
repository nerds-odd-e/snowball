package com.odde.snowball.controller;

import com.odde.snowball.service.LocationProviderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/countries")
public class CountryController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        respondWithJSON(resp, new LocationProviderService().getAllCountryNames());
    }
}

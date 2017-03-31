package com.odde.massivemailer.controller;


import com.odde.massivemailer.serialiser.AppGson;
import com.odde.massivemailer.service.LocationProviderService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by csd on 31/3/17.
 */
@WebServlet("/locations")
public class LocationController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocationProviderService locationProviderService = new LocationProviderService();
        String convertedContactToJSON = AppGson.getGson().toJson(locationProviderService.getSupportedLocations());
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertedContactToJSON);
    }
}

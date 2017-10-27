package com.odde.massivemailer.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.serialiser.AppGson;
import com.odde.massivemailer.service.LocationProviderService;

@WebServlet("/contacts")
public class ContactsController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg;
        LocationProviderService locationProviderService = new LocationProviderService();
        String city = req.getParameter("city");
        String country = req.getParameter("country");
        Location cityLocation = locationProviderService.getLocationForName(city);
        String location;
        if( cityLocation == null ){
            location =  country + "/" + city;
            Location storedLocation = locationProviderService.getLocationForName(location);
            if (storedLocation == null) {
                locationProviderService.addLocation(country, city);
            }
        } else {
            location = city;
        }

        ContactPerson contact = new ContactPerson("todo name", req.getParameter("email"), "todo last name", "todo company",location);

        try {
            contact.saveIt();
            resultMsg = "status=success&msg=Add contact successfully";
        } catch (Exception e) {
            resultMsg = "status=failed&msg=" + e.getMessage();
        }

        resp.sendRedirect("contactlist.jsp?" + resultMsg);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String convertedContactToJSON = null;
        if (req.getParameter("email") == null) {
            convertedContactToJSON = AppGson.getGson().toJson(ContactPerson.findAll());
        } else {
            convertedContactToJSON = AppGson.getGson().toJson(ContactPerson.getContactByEmail(req.getParameter("email")));
        }
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertedContactToJSON);
    }
}

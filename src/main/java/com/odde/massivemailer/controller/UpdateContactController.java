package com.odde.massivemailer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.LocationProviderService;

@WebServlet("/editContact")
public class UpdateContactController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ContactPerson contactPerson = ContactPerson.getContactByEmail(req.getParameter("email"));

        LocationProviderService locationProviderService = new LocationProviderService();
        String city = req.getParameter("city");
        String country = req.getParameter("country");
        String location = country + "/" + city;
        locationProviderService.cacheLocation(city, country, location);

        if (contactPerson != null) {
            contactPerson.setName(req.getParameter("name"));
            contactPerson.setLastname(req.getParameter("lastname"));
            contactPerson.setCompany(req.getParameter("company"));
            contactPerson.setLocation(location);
            contactPerson.saveIt();
            resp.sendRedirect("contactlist.jsp");

        }

    }

}

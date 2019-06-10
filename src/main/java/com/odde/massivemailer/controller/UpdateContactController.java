package com.odde.massivemailer.controller;

import java.io.IOException;

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

        if (contactPerson != null) {
            contactPerson.setFirstName(req.getParameter("name"));
            contactPerson.setLastName(req.getParameter("lastName"));
            contactPerson.setCompany(req.getParameter("company"));
            contactPerson.setCity(city);
            contactPerson.setCountry(country);
            locationProviderService.cacheLocation(city, country, contactPerson.location());
            contactPerson.save();
            resp.sendRedirect("contactlist.jsp");

        }

    }

}

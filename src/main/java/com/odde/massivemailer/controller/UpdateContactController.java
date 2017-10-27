package com.odde.massivemailer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Location;
import com.odde.massivemailer.service.LocationProviderService;

@WebServlet("/editContact")
public class UpdateContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ContactPerson contactPerson = ContactPerson.getContactByEmail(req.getParameter("email"));

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

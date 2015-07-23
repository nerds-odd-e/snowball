package com.odde.massivemailer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;

public class ShowContactController extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getOutputStream().print(convertContactToJSON(getContactData()));
	}
	
	public List<ContactPerson> getContactData() {
		List<ContactPerson> cpList = new ArrayList<ContactPerson>();
		ContactPerson cp = new ContactPerson();
		cp.setId(1);
		cp.setName("mail@hotmail.com");
		cpList.add(cp);
		return cpList;
	}
	
	public String convertContactToJSON(List<ContactPerson> contactList) {
		return new Gson().toJson(contactList);
	}
}

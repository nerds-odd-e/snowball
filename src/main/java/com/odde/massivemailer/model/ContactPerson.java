package com.odde.massivemailer.model;

import java.util.HashMap;
import java.util.Set;

public class ContactPerson {
	private int id;
	private String name;
	private String email;
	private String lastname;
	private String company;
	private HashMap<String, String> attribute = new HashMap<>();

	public ContactPerson(){
		
	}
	
	public ContactPerson(String name, String email, String lastname){
		this(name, email, lastname, "");
	}
	
	public ContactPerson(String name, String email, String lastname, String company) {
		setName(name);
		setLastname(lastname);
		setCompany(company);
		this.email = email;

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return getAttribute("FirstName");
	}
	public void setName(String name) {
		setAttribute("FirstName", name);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastname() {
		return getAttribute("LastName");
	}
	public void setLastname(String lastname) {
		setAttribute("LastName", lastname);
	}

	public String getCompany() {
		return attribute.get("Company");
	}

	public void setCompany(String company) {
		attribute.put("Company", company);
	}

	public void setAttribute(String name, String value) {
		attribute.put(name, value);
	}

	public String getAttribute(String name) {
		return attribute.get(name);
	}

	public Set<String> getAllAttributeNames() {
		return attribute.keySet();
	}
}

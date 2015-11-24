package com.odde.massivemailer.model;

public class ContactPerson {
	private int id;
	private String name;
	private String email;
	private String lastname;
	private String company;
	
	public ContactPerson(){
		
	}
	
	public ContactPerson(String name, String email, String lastname){
		this(name, email, lastname, "");
	}
	
	public ContactPerson(String name, String email, String lastname, String company) {
		this.name = name;
		this.email = email;
		this.lastname = lastname;
		this.company = company;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
}

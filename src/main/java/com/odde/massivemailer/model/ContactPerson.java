package com.odde.massivemailer.model;

public class ContactPerson {
	private int id;
	private String name;
	private String email;
	private String lastname;
	
	public ContactPerson(){
		
	}
	
	public ContactPerson(String name, String email, String lastname){
		this.name = name;
		this.email = email;
		this.lastname = lastname;
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
}

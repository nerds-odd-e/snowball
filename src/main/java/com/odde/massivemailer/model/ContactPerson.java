package com.odde.massivemailer.model;

public class ContactPerson {
	private int id;
	private String name;
	private String email;
	private String lastname;

	public static final String FIRSTNAME = "FirstName";
	public static final String LASTNAME = "LastName";
	public static final String EMAIL = "Email";

	public static final String[] ATTRIBUTES = new String[] {FIRSTNAME, LASTNAME, EMAIL};

	public ContactPerson(){
		
	}
	
	public ContactPerson(String name, String email, String lastname){
		this.name = name;
		this.email = email;
		this.lastname = lastname;
	}

	public String getAttributeValue(String keyName)
	{
		switch (keyName)
		{
			case FIRSTNAME:
				return this.getName();
			case LASTNAME:
				return this.getLastname();
			case EMAIL:
				return this.getEmail();
			default:
				return "";
		}

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

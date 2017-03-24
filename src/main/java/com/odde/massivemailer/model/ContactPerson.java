package com.odde.massivemailer.model;

import com.odde.massivemailer.exception.InvalidEmailException;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


@Table("contact_people")
public class ContactPerson extends Model {
	private int id;
	private String name;
	private String email;
	private String lastname;
	private String company;

	public static final String FIRSTNAME = "FirstName";
	public static final String LASTNAME = "LastName";
	public static final String EMAIL = "Email";
	public static final String COMPANY = "Company";

	public Map<String, String> attributes = new HashMap<>();

	public ContactPerson(){

	}

	public ContactPerson(String name, String email, String lastname) {
		this(name, email, lastname, "");
	}

	public ContactPerson(String name, String email, String lastname, String company) {
		setName(name);

		try {
			new InternetAddress(email).validate();
		}catch(AddressException ae){
			throw new InvalidEmailException("Invalid email", ae);
		}

		setEmail(email);

		setLastname(lastname);
		setCompany(company);
	}

	public String getName() { return getAttribute(FIRSTNAME); }
	public void setName(String name) {
		setAttribute(FIRSTNAME, name);
	}
	public String getEmail() {
		return getAttribute(EMAIL);
	}
	public void setEmail(String email) {
		setAttribute(EMAIL, email);
	}
	public String getLastname() {
		return getAttribute(LASTNAME);
	}
	public void setLastname(String lastname) {
		setAttribute(LASTNAME, lastname);
	}

	public String getCompany() {
		return getAttribute(COMPANY);
	}

	public void setCompany(String company) {
		setAttribute(COMPANY, company);
	}

	private void setAttribute(String name, String value)
	{
		attributes.put(name, value);
		set(name.toLowerCase(), value);
	}

	public String getAttribute(String name)
	{
		return attributes.containsKey(name) ? attributes.get(name) : "";
	}

	public Set<String> getAttributeKeys()
	{
		return attributes.keySet();
	}


	@Override
	public boolean equals(Object obj) {
		ContactPerson otherContact = (ContactPerson)obj;
		return name.equals(otherContact.name) && email.equals(otherContact.email) && lastname.equals(otherContact.lastname) && company.equals(otherContact.company);
	}

}

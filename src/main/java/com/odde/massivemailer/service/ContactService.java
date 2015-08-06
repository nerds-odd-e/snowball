package com.odde.massivemailer.service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.odde.massivemailer.model.ContactPerson;

public interface ContactService {

	List<ContactPerson> getContactList();

	int addNewContact(String name,String email);
	String addContact(String name,String email);
	int updateContact(ContactPerson contactPerson, Statement statement) throws SQLException;
}
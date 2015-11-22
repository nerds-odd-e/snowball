package com.odde.massivemailer.service;

import java.sql.SQLException;
import java.util.List;

import com.odde.massivemailer.model.ContactPerson;

public interface ContactService {

	List<ContactPerson> getContactList();

	int addNewContact(String name,String email);

	void updateContact(ContactPerson contactPerson) throws SQLException;
	boolean addContact(ContactPerson contact);
}
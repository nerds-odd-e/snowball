package com.odde.massivemailer.service;

import java.sql.SQLException;
import java.util.List;

import com.odde.massivemailer.model.ContactPerson;

public interface ContactService {

	List<ContactPerson> getContactList();

	int addNewContact(String name,String email);
	int addNewContact(String name, String email, String company);

	void updateContact(ContactPerson contactPerson) throws SQLException;
	boolean addContact(ContactPerson contact);

<<<<<<< d9cc6196f56a9405d8f2923f802f99b51f536b54
	ContactPerson getContactByEmail(String email) throws SQLException;

=======
	List<ContactPerson> getContactListFromCompany(String company) throws SQLException;

	int addNewContact(String name, String email, String lastname, String company);
>>>>>>> Add getContactList by company
}
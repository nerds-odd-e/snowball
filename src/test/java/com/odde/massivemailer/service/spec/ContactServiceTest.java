package com.odde.massivemailer.service.spec;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.impl.SqliteContact;

public class ContactServiceTest {

	private SqliteContact service = new SqliteContact();
	private Connection connection;
	private Statement stmt;
	//private ResultSet resultSet;
	private List<ContactPerson> contactList;
	private List<ContactPerson> contactCompanyA;
	private List<ContactPerson> contactCompanyB;
	private List<ContactPerson> contactCompanyAll;

	@Before
	public void setupConnection() throws ClassNotFoundException, SQLException {
		service.connectDB("jdbc:sqlite:oddemail.db");
		connection = service.getConnection();
		stmt = service.getStatement();
		stmt.executeUpdate(
				"DROP TABLE mail");
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50) NOT NULL, email VARCHAR(50), lastname VARCHAR(50), company VARCHAR(50))");
		stmt.executeUpdate("DELETE FROM mail");

<<<<<<< d9cc6196f56a9405d8f2923f802f99b51f536b54
	@Ignore
=======
		contactCompanyA = new ArrayList<ContactPerson>();
		contactCompanyA.add(new ContactPerson("nameA1", "email1@companyA.com", "lastnameA1", "companyA"));
		contactCompanyA.add(new ContactPerson("nameA2", "email2@companyA.com", "lastnameA2", "companyA"));
		contactCompanyA.add(new ContactPerson("nameA3", "email3@companyA.com", "lastnameA3", "companyA"));
		contactCompanyA.add(new ContactPerson("nameA4", "email4@companyA.com", "lastnameA4", "companyA"));

		contactCompanyB = new ArrayList<ContactPerson>();
		contactCompanyB.add(new ContactPerson("nameB1", "email1@companyB.com", "lastnameA1", "companyB"));
		contactCompanyB.add(new ContactPerson("nameB2", "email2@companyB.com", "lastnameA2", "companyB"));
		
		contactCompanyAll = new ArrayList<ContactPerson>();
		contactCompanyAll.add(new ContactPerson("nameA1", "email1@companyA.com", "lastnameA1", "companyA"));
		contactCompanyAll.add(new ContactPerson("nameA2", "email2@companyA.com", "lastnameA2", "companyA"));
		contactCompanyAll.add(new ContactPerson("nameA3", "email3@companyA.com", "lastnameA3", "companyA"));
		contactCompanyAll.add(new ContactPerson("nameA4", "email4@companyA.com", "lastnameA4", "companyA"));
		contactCompanyAll.add(new ContactPerson("nameB1", "email1@companyB.com", "lastnameA1", "companyB"));
		contactCompanyAll.add(new ContactPerson("nameB2", "email2@companyB.com", "lastnameA2", "companyB"));
		
		//stmt.executeUpdate("INSERT INTO mail(name, email) VALUES ('name 1', 'aaa@gmail.com')");
		for(ContactPerson cList: contactCompanyA)
			stmt.executeUpdate("INSERT INTO mail(name, email,lastname,company) VALUES ('"+cList.getName()+"', '"+cList.getEmail()+"','"+cList.getLastname()+"','"+cList.getCompany()+"')");
		for(ContactPerson cList: contactCompanyB)
			stmt.executeUpdate("INSERT INTO mail(name, email,lastname,company) VALUES ('"+cList.getName()+"', '"+cList.getEmail()+"','"+cList.getLastname()+"','"+cList.getCompany()+"')");
			
	}
	
>>>>>>> Add getContactList by company
	@Test
	public void checkContactReturned() throws SQLException, ClassNotFoundException {
		contactList = service.getContactList();
		assertArrayEquals(contactCompanyAll.toArray(), contactList.toArray());
	}
	
	@Test
	public void getContactListFromCompany() throws SQLException, ClassNotFoundException {
		contactList = service.getContactListFromCompany("companyA");
		assertArrayEquals(contactCompanyA.toArray(), contactList.toArray());
	}

	@Ignore
	@Test
	public void addNewContactTest() throws SQLException {
		int rowAffected = service.addNewContact("AddNewContact","MyTest@email.com");
		assertEquals(1, rowAffected);
	}
<<<<<<< d9cc6196f56a9405d8f2923f802f99b51f536b54

	@Ignore
	@Test
	public void addNewContactFailedTest() throws SQLException {
		int rowAffected = service.addNewContact("name 1","aaa@gmail.com");
		assertEquals(0, rowAffected);
	}

	@Ignore
=======
	
//	@Test
//	public void addNewContactFailedTest() throws SQLException {
//		int rowAffected = service.addNewContact("name 1","aaa@gmail.com");
//		assertEquals(0, rowAffected);
//	}
	
>>>>>>> Add getContactList by company
	@Test
	public void addContactSuccess() {
		assertTrue(service.addContact(new ContactPerson("contact name", "a@b.com", "", "")));
	}

	@Ignore
	@Test
	public void addExistingContact() {
		service.addContact(new ContactPerson("contact name", "a@b.com", "", ""));
		assertFalse(service.addContact(new ContactPerson("contact name", "a@b.com", "", "")));
	}
	
	@After
	public void resetConnection() throws SQLException {
		stmt.close();
		connection.close();
	}
}
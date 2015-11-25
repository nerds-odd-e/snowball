package com.odde.massivemailer.service.spec;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	private ResultSet resultSet;
	private List<ContactPerson> contactList;

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
		stmt.executeUpdate("INSERT INTO mail(name, email) VALUES ('name 1', 'aaa@gmail.com')");
	}

	@Ignore
	@Test
	public void checkContactReturned() throws SQLException, ClassNotFoundException {
		contactList = service.getContactList();
		for(ContactPerson contact : contactList) {
			assertEquals("name 1", contact.getName());
		}
	}

	@Ignore
	@Test
	public void addNewContactTest() throws SQLException {
		int rowAffected = service.addNewContact("AddNewContact","MyTest@email.com");
		assertEquals(1, rowAffected);
	}

	@Ignore
	@Test
	public void addNewContactFailedTest() throws SQLException {
		int rowAffected = service.addNewContact("name 1","aaa@gmail.com");
		assertEquals(0, rowAffected);
	}

	@Ignore
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
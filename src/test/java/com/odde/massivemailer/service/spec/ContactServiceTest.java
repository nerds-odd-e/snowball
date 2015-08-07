package com.odde.massivemailer.service.spec;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
				"CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50) NOT NULL, email VARCHAR(50), lastname VARCHAR(50))");
		stmt.executeUpdate("DELETE FROM mail");
		stmt.executeUpdate("INSERT INTO mail(name, email) VALUES ('name 1', 'aaa@gmail.com')");
	}

	@Test
	public void checkContactReturned() throws SQLException, ClassNotFoundException {
		contactList = service.getContactList();
		for(ContactPerson contact : contactList) {
			assertEquals("name 1", contact.getName());
		}
	}
	
	@Test
	public void addNewContactTest() throws SQLException {
		int rowAffected = service.addNewContact("AddNewContact","MyTest@email.com");
		assertEquals(1, rowAffected);
	}
	
	@Test
	public void addNewContactFailedTest() throws SQLException {
		int rowAffected = service.addNewContact("name 1","aaa@gmail.com");
		assertEquals(0, rowAffected);
	}
	
	@Test
	public void addContactSuccess() {
		String result = service.addContact("contact name", "a@b.com");
		assertEquals("status=success&msg=Add contact successfully", result);
	}
	
	@Test
	public void addExistingContact() {
		service.addContact("contact name", "a@b.com");
		String result = service.addContact("contact name", "a@b.com");
		assertEquals("status=failed&msg=Email a@b.com is already exist", result);
	}
	
	@After
	public void resetConnection() throws SQLException {
		stmt.close();
		connection.close();
	}
}
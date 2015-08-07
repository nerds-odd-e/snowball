package com.odde.massivemailer.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.odde.massivemailer.model.ContactPerson;

@RunWith(MockitoJUnitRunner.class)
public class SqliteContactTest {

	private SqliteContact sqliteContact;

	@Mock
	private Statement mockStatement;

	@Mock
	private Connection mockConnection;

	@Before
	public void setUp() throws SQLException, ClassNotFoundException {
		sqliteContact = new SqliteContact();
		Mockito.when(mockStatement.executeUpdate(Matchers.anyString()))
				.thenReturn(1);
		sqliteContact.setStatement(mockStatement);
	}
	
	@After
	public void tearDown() {
		sqliteContact.closeConnection();
	}

	@Test
	public void updateContactTerry() throws SQLException,
			ClassNotFoundException {

		ContactPerson contactPerson = new ContactPerson("terry",
				"terry@gmail.com", "e");
		sqliteContact.setStatement(mockStatement);
		sqliteContact.updateContact(contactPerson);

		Mockito.verify(mockStatement)
				.executeUpdate(
						"UPDATE mail SET name='terry',email='terry@gmail.com',lastname='e' where email='terry@gmail.com'");

	}

	@Test
	public void updateContactRoof() throws SQLException, ClassNotFoundException {

		ContactPerson contactPerson = new ContactPerson("roof",
				"roof@gmail.com", "e");
		sqliteContact.setStatement(mockStatement);
		sqliteContact.updateContact(contactPerson);

		Mockito.verify(mockStatement)
				.executeUpdate(
						"UPDATE mail SET name='roof',email='roof@gmail.com',lastname='e' where email='roof@gmail.com'");
	}

}

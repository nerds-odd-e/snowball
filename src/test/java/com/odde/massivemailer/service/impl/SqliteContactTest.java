package com.odde.massivemailer.service.impl;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import com.odde.massivemailer.model.ContactPerson;

@RunWith(MockitoJUnitRunner.class)
public class SqliteContactTest {

	private SqliteContact sqliteContact;

	@Mock
	private PreparedStatement mockPreparedStatement;
	
	@Mock
	private Statement mockStatement;

	@Mock
	private Connection mockConnection;

	@Before
	public void setUp() throws SQLException, ClassNotFoundException {
		sqliteContact = new SqliteContact();
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(Matchers.anyString())).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeQuery()).thenReturn(null);
		sqliteContact.setConnection(mockConnection);
		
		// for addContact test
		
	}
	
	@After
	public void tearDown() {
//		sqliteContact.closeConnection();
	}

	@Test
	public void testIsTableExists() {

	}

	@Test
	public void testUpdateContact() throws SQLException{
		
		ContactPerson contactPerson = new ContactPerson("terry",
				"roof@gmail.com", "e", "ComA");
		sqliteContact.updateContact(contactPerson);
		verify(mockPreparedStatement).executeUpdate();
	}
	
	@Test
	public void testUpdateContactWithCompany() throws SQLException{
		
		ContactPerson contactPerson = new ContactPerson("terry",
				"roof@gmail.com", "e", "myCompany");
		sqliteContact.updateContact(contactPerson);
		verify(mockPreparedStatement).executeUpdate();
	}

	
	@Test(expected = SQLException.class)
	public void testUpdateContactFailed() throws SQLException{
		when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException());
		ContactPerson contactPerson = new ContactPerson("terry",
				"roof@gmail.com", "e", "ComA");
		sqliteContact.updateContact(contactPerson);
	}
	
	
	@Test(expected = SQLException.class)
	public void testUpdateContactWithCompanyFailed() throws SQLException{
		when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException());
		ContactPerson contactPerson = new ContactPerson("terry",
				"roof@gmail.com", "e", "myCompany");
		sqliteContact.updateContact(contactPerson);
	}

	@Test
	public void testUpdateContactMultipleTimes() throws SQLException{
		ContactPerson contactPerson = new ContactPerson("terry",
				"roof@gmail.com", "e", "ComA");
		sqliteContact.updateContact(contactPerson);
		sqliteContact.setConnection(mockConnection);
		ContactPerson contactPerson2 = new ContactPerson("terry2",
				"roof@gmail.com", "e", "ComA");
		sqliteContact.updateContact(contactPerson2);
	}

	@Test
	public void testGetContactByEmail() throws SQLException{

		// Arrange
		ResultSet resultSetMock = mock(ResultSet.class);
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(1);
		when(resultSetMock.getString("name")).thenReturn("roof");
		when(resultSetMock.getString("email")).thenReturn("roof@gmail.com");
		when(resultSetMock.getString("lastname")).thenReturn("dd");
		when(mockPreparedStatement.executeQuery()).thenReturn(resultSetMock);

		// Act
		ContactPerson person = sqliteContact.getContactByEmail("roof@gmail.com");

		// Assert
		assertEquals(1, person.getId());
		assertEquals("roof", person.getName());
		assertEquals("roof@gmail.com", person.getEmail());
		assertEquals("dd", person.getLastname());

		verify(mockPreparedStatement).executeQuery();

	}

	@Test
	public void testGetContactByEmailNotFound() throws SQLException{

		// Arrange
		ResultSet resultSetMock = mock(ResultSet.class);
		when(resultSetMock.next()).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(1);
		when(resultSetMock.getString("name")).thenReturn("roof");
		when(resultSetMock.getString("email")).thenReturn("roof@gmail.com");
		when(resultSetMock.getString("lastname")).thenReturn("dd");
		when(mockPreparedStatement.executeQuery()).thenReturn(resultSetMock);

		// Act
		ContactPerson person = sqliteContact.getContactByEmail("roof@gmail.com");

		// Assert
		assertEquals(null, person);

		verify(mockPreparedStatement).executeQuery();

	}

}

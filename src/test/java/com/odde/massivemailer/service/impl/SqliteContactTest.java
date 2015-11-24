package com.odde.massivemailer.service.impl;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	private Connection mockConnection;

	@Before
	public void setUp() throws SQLException, ClassNotFoundException {
		sqliteContact = new SqliteContact();
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(Matchers.anyString())).thenReturn(mockPreparedStatement);
		sqliteContact.setConnection(mockConnection);
	}
	
	@After
	public void tearDown() {
		sqliteContact.closeConnection();
	}
	
	@Test
	public void testUpdateContact() throws SQLException{
		
		ContactPerson contactPerson = new ContactPerson("terry",
				"roof@gmail.com", "e", "ComA");
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

}

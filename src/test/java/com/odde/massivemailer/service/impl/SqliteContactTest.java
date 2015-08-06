package com.odde.massivemailer.service.impl;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.odde.massivemailer.model.ContactPerson;

@RunWith(MockitoJUnitRunner.class)
public class SqliteContactTest {	
	
	private SqliteContact sqliteContact = new SqliteContact();
	
	@Mock
	private Statement statement;

	@Test
	public void updateContactTerry() throws SQLException, ClassNotFoundException {
		Mockito.when(statement.executeUpdate(Matchers.anyString())).thenReturn(1);
		ContactPerson contactPerson = new ContactPerson("terry","terry@gmail.com","e");
		int rowAffected = sqliteContact.updateContact(contactPerson, statement);
		assertEquals(1, rowAffected);
		
		Mockito.verify(statement).executeUpdate("UPDATE mail SET name='terry',email='terry@gmail.com',lastname='e' where email='terry@gmail.com'");
	}
	
	@Test
	public void updateContactRoof() throws SQLException, ClassNotFoundException {
		Mockito.when(statement.executeUpdate(Matchers.anyString())).thenReturn(1);
		ContactPerson contactPerson = new ContactPerson("roof","roof@gmail.com","e");
		int rowAffected = sqliteContact.updateContact(contactPerson, statement);
		assertEquals(1, rowAffected);
		
		Mockito.verify(statement).executeUpdate("UPDATE mail SET name='roof',email='roof@gmail.com',lastname='e' where email='roof@gmail.com'");
	}

}

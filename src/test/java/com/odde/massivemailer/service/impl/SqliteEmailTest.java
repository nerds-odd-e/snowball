package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SqliteEmailTest extends SqliteBase {

	private SqliteEmail sqliteEmail;


	@Before
	public void setUp() throws SQLException, ClassNotFoundException,Exception {
		sqliteEmail = new SqliteEmail();
		statement=openConnection();

		sqliteEmail.createIfNotExistTable();
		statement.execute("delete from  mail ;");
		statement.execute("insert into mail values ('1', 'Promotional test','',null,'','','');");

		// for addContact test
		
	}

	@After
	public void tearDown() throws Exception{
		statement.execute("delete from  mail ;");
//		sqliteContact.closeConnection();
	}

	@Test @Ignore
	public void testGetEmailList() throws SQLException{

		// Act
		List<Notification> emailList = sqliteEmail.getSentEmailList();
		assertEquals(1, emailList.size());
		assertEquals("1", emailList.get(0).getNotificationId());
		assertEquals("Promotional test", emailList.get(0).getSubject());
		assertNull(emailList.get(0).getSentDate());
	}

}

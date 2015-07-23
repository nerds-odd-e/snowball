package com.odde.massivemailer.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.odde.massivemailer.model.ContactPerson;

public class ContactService {
	private String selectMailSql = "SELECT id, name FROM mail";
	private String dbName = "jdbc:sqlite:oddemail.db";
	private List<ContactPerson> contactList;

	public Connection connectDB(String url) throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection(url);
		Statement statement = connection.createStatement();
		statement.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50) NOT NULL)");
		return connection;
	}

	public List<ContactPerson> getContactList() {
		Connection connection;
		ResultSet resultSet = null;
		try {
			connection = this.connectDB(dbName);
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(this.selectMailSql);
			populateContactList(resultSet);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	public int addNewContact(String name) {
		Connection connection;
		Statement statement;
		int rowAffected = 0;
		try {
			connection = this.connectDB(dbName);
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT name FROM mail WHERE name='" + name + "'");
			if(!resultSet.next())
				rowAffected = statement.executeUpdate("INSERT INTO mail(name) VALUES ('"+ name +"')");				
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rowAffected;
	}
	
	
	private void populateContactList(ResultSet resultSet) throws SQLException{
		contactList = new ArrayList<ContactPerson>();
		while (resultSet.next()) {
			ContactPerson contact = new ContactPerson();
			contact.setId(resultSet.getInt("id"));
			contact.setName(resultSet.getString("name"));
			contactList.add(contact);
		}
	}
}

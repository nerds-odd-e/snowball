package com.odde.massivemailer.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;

public class SqliteContact implements ContactService {
	private String selectMailSql = "SELECT id, name, email, lastname FROM mail";
	private String dbName = "jdbc:sqlite:oddemail.db";
	private List<ContactPerson> contactList;
	private Statement statement;
	private Connection connection;

	public SqliteContact() {
		try {
			openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void connectDB(String url) throws ClassNotFoundException,
			SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection(url);
	}

	@Override
	public List<ContactPerson> getContactList() {
		ResultSet resultSet = null;
		try {
			openConnection();
			createIfNotExistTable();

			resultSet = statement.executeQuery(this.selectMailSql);
			populateContactList(resultSet);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return contactList;
	}

	private void createIfNotExistTable() throws SQLException {

		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50), email VARCHAR(50) NOT NULL, lastname VARCHAR(50))");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odde.massivemailer.service.ContactService#addNewContact(java.lang
	 * .String)
	 */
	@Override
	public int addNewContact(String name, String email) {
		int rowAffected = 0;
		try {
			openConnection();
			rowAffected = saveContactToDatabase(name, email);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return rowAffected;
	}

	private int saveContactToDatabase(String name, String email)
			throws SQLException {
		int rowAffected = 0;
		if (!contactExisted(email))
			rowAffected = statement
					.executeUpdate("INSERT INTO mail(name,email) VALUES ('"
							+ name + "', '" + email + "')");
		return rowAffected;
	}

	private boolean contactExisted(String email) throws SQLException {
		ResultSet resultSet = statement
				.executeQuery("SELECT email FROM mail WHERE email='" + email
						+ "'");
		if (resultSet.next()) {
			return true;
		}
		return false;
	}

	private void populateContactList(ResultSet resultSet) throws SQLException {
		contactList = new ArrayList<ContactPerson>();
		while (resultSet.next()) {
			ContactPerson contact = new ContactPerson();
			contact.setId(resultSet.getInt("id"));
			contact.setName(resultSet.getString("name"));
			contact.setEmail(resultSet.getString("email"));
			contact.setLastname(resultSet.getString("lastname"));
			contactList.add(contact);
		}
	}

	@Override
	public boolean addContact(ContactPerson contact) {
		return addNewContact(contact.getName(), contact.getEmail()) > 0;
	}

	@Override
	public void updateContact(ContactPerson contactPerson) throws SQLException {
		String sql = "UPDATE mail SET name=?, email=?, lastname=? where email=?";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, contactPerson.getName());
		preparedStatement.setString(2, contactPerson.getEmail());
		preparedStatement.setString(3, contactPerson.getLastname());
		preparedStatement.setString(4, contactPerson.getEmail());

		preparedStatement.executeUpdate();
	}

	private Statement openConnection() throws ClassNotFoundException,
			SQLException {
		this.connectDB(dbName);
		statement = getStatement();
		return statement;
	}

	public void closeConnection() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Statement openConnections() throws ClassNotFoundException,
			SQLException {
		return openConnection();
	}

	public Statement getStatement() throws SQLException {
		statement = connection.createStatement();
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;		
	}

}

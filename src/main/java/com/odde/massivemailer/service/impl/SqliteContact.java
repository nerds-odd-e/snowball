package com.odde.massivemailer.service.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;

public class SqliteContact  extends SqliteBase implements ContactService {
	private String selectMailSql = "SELECT id, name, email, lastname, company FROM mail";
	private List<ContactPerson> contactList;

	private String selectMailFromCompanySql = "SELECT id, name, email, lastname, company FROM mail where company = ";

	public SqliteContact() {
		try {
			openConnection();
			createIfNotExistTable();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ContactPerson> getContactList() {
		ResultSet resultSet = null;
		try {
			openConnection();

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

		//if(!isTableExists("mail"))
			statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50), email VARCHAR(50) NOT NULL, lastname VARCHAR(50), company VARCHAR(50))");
	}

	private boolean isTableExists(String name)
	{
		try {
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, name, null);
			return (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odde.massivemailer.service.ContactService#addNewContact(java.lang
	 * .String)
	 */
	@Override
	public int addNewContact(String name, String email ) {
		return addNewContact(name, email, "");
	}
	
	@Override
	public int addNewContact(String name, String email, String company) {
		return addNewContact(name, email, "", company);
	}
	
	@Override
	public int addNewContact(String name, String email, String lastname, String company) {
		int rowAffected = 0;
		try {
			openConnection();
			rowAffected = saveContactToDatabase(name, email, lastname, company);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return rowAffected;
	}

	@Override
	public void destroyAll() {
		try {
			openConnection();
			statement.execute("DELETE FROM mail;");
			statement.executeUpdate("drop table if exists Template;CREATE TABLE Template (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TemplateName VARCHAR(255) NOT NULL, Subject VARCHAR(255), Content NVARCHAR(5000))");
			statement.executeUpdate("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
			statement.execute("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	private int saveContactToDatabase(String name, String email, String lastname, String company)
			throws SQLException {
		int rowAffected = 0;
		if (!contactExisted(email))
			rowAffected = statement
					.executeUpdate("INSERT INTO mail(name,email,lastname,company) VALUES ('"
							+ name + "', '" + email + "','" + lastname + "','" + company + "')");
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
			contact.setCompany(resultSet.getString("company"));
			contactList.add(contact);
		}
	}

	@Override
	public boolean addContact(ContactPerson contact) {
		return addNewContact(contact.getName(), contact.getEmail(), contact.getLastname(), contact.getCompany()) > 0;
	}

	@Override
	public void updateContact(ContactPerson contactPerson) throws SQLException {

		try {
			openConnection();

			String sql = "UPDATE mail SET name=?, email=?, lastname=?, company=? where email=?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, contactPerson.getName());
			preparedStatement.setString(2, contactPerson.getEmail());
			preparedStatement.setString(3, contactPerson.getLastname());
			preparedStatement.setString(4, contactPerson.getCompany());
			preparedStatement.setString(5, contactPerson.getEmail());

			preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	@Override
	public ContactPerson getContactByEmail(String email) throws SQLException {
		try {
			openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mail WHERE email=?");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();

			if ((resultSet != null) && (resultSet.next())) {
				ContactPerson contact = new ContactPerson();
				contact.setId(resultSet.getInt("id"));
				contact.setName(resultSet.getString("name"));
				contact.setEmail(resultSet.getString("email"));
				contact.setLastname(resultSet.getString("lastname"));
				contact.setCompany(resultSet.getString("company"));
				return contact;
			}
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return null;

	}
	
	@Override
	public List<ContactPerson> getContactListFromCompany(String company) throws SQLException {
				
		ResultSet resultSet = null;
		try {
			openConnection();
			createIfNotExistTable();

			resultSet = statement.executeQuery(this.selectMailFromCompanySql + "'" + company + "'");
			populateContactList(resultSet);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return contactList;	

	}

}

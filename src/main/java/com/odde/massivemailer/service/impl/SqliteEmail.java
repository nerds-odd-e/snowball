package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.EmailService;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqliteEmail extends SqliteBase implements EmailService {
	private String selectSentEmailSql = "SELECT id, subject, sentdate FROM mail order by sentdate DESC";
	private List<Mail> sentEmailList;

	public void setSentEmailList(List<Mail> sentEmailList) {
		this.sentEmailList = sentEmailList;
	}

	private String selectMailFromCompanySql = "SELECT id, name, email, lastname, company FROM mail where company = ";

	public SqliteEmail() {
		try {
			openConnection();
			createIfNotExistTable();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}



	@Override
	public List<Mail> getSentEmailList()  {
		ResultSet resultSet = null;
		try {
			openConnection();
			resultSet = statement.executeQuery(this.selectSentEmailSql);
			populateSentEmailList(resultSet);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return sentEmailList;
	}


	private void populateSentEmailList(ResultSet resultSet) throws SQLException {
		sentEmailList = new ArrayList<Mail>();
		while (resultSet.next()) {
			Mail mail = new Mail();

			mail.setKey(resultSet.getString("id"));
			mail.setSubject(resultSet.getString("subject"));
			mail.setSentDate(resultSet.getTimestamp("sentdate"));
			sentEmailList.add(mail);
		}
	}


	public void createIfNotExistTable() throws SQLException {

		//if(!isTableExists("mail"))
			statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, subject VARCHAR(50), " +
						"name VARCHAR(50), sentdate timestamp, email VARCHAR(50) NOT NULL, lastname VARCHAR(50), company VARCHAR(50))");
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
}

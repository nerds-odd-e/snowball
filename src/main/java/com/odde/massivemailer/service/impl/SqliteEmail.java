package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.EmailService;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class SqliteEmail extends SqliteBase implements EmailService {
	private String selectSentEmailSql = "SELECT notification_id, subject, sent_at FROM notifications order by sent_at DESC";
	private List<Mail> sentEmailList;
    private static Logger emailLogger = Logger.getLogger(SqliteEmail.class.getName());


	private String selectOpenedEmailCounterSQL ="select " ;
	private List<Mail> openedEmailCounterList;

	public void setSentEmailList(List<Mail> sentEmailList) {
		this.sentEmailList = sentEmailList;
	}
	
	public void setOpenedEmailCounterList(List<Mail> openedEmailCounterList) {
		this.openedEmailCounterList = openedEmailCounterList;
	}

	private String selectMailFromCompanySql = "SELECT id, name, email, lastname, company FROM mail where company = ";

	public SqliteEmail() {
		try {
			openConnection();
			//createIfNotExistTable();
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

			mail.setKey(resultSet.getString("notification_id"));
			mail.setSubject(resultSet.getString("subject"));
			mail.setSentDate(resultSet.getTimestamp("sent_at"));
			emailLogger.info("notification id =" + mail.getKey());
			emailLogger.info("subject =" + mail.getSubject());
			emailLogger.info("sent_at =" + mail.getSentDate());

			sentEmailList.add(mail);
		}
	}

	@Override
	public List<Mail> getOpenedEmailCountList()  {
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

	private void populateOpenedEmailCounterList(ResultSet resultSet) throws SQLException {
		openedEmailCounterList = new ArrayList<Mail>();
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
	    //Not required
	}

	@Override
	public void setOpenedEmailCountList(int i) {

	}

	@Override
	public String getEmailCounterJson(int email_id) {
		return null;
	}
}

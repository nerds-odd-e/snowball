package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.EmailService;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class SqliteEmail extends SqliteBase implements EmailService {
	private String selectSentEmailSql = "SELECT notification_id, subject, sent_at FROM notifications order by sent_at DESC";
	private List<Notification> sentEmailList;
    private static Logger emailLogger = Logger.getLogger(SqliteEmail.class.getName());

	private String selectOpenedEmailCounterSQL ="select " ;
	private List<Notification> openedEmailCounterList;
	private ArrayList<NotificationDetail> notifications;

	public void setSentEmailList(List<Notification> sentEmailList) {
		this.sentEmailList = sentEmailList;
	}
	
	public void setOpenedEmailCounterList(List<Notification> openedEmailCounterList) {
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
		notifications = new ArrayList<NotificationDetail>();
	}



	@Override
	public List<Notification> getSentEmailList()  {
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
		sentEmailList = new ArrayList<Notification>();
		while (resultSet.next()) {
			Notification mail = new Notification();

			mail.setId(resultSet.getLong("notification_id"));
			mail.setSubject(resultSet.getString("subject"));
			mail.setSentDate(resultSet.getTimestamp("sent_at"));
			emailLogger.info("notification id =" + mail.getId());
			emailLogger.info("subject =" + mail.getSubject());
			//emailLogger.info("sent_at =" + mail.getSentDate());

			sentEmailList.add(mail);
		}
	}

	@Override
	public List<Notification> getOpenedEmailCountList()  {
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
		openedEmailCounterList = new ArrayList<Notification>();
		while (resultSet.next()) {
			Notification mail = new Notification();

			mail.setNotificationId(resultSet.getLong("notification_id"));
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
	public String getEmailCounterJson(int sender_email_id) {

		ArrayList<NotificationDetail> array = getReceipentOfEmail(sender_email_id);
		if (array.isEmpty())
		      return "[]";
		else
			return "["+array.get(0).toJSON()+"]";
	}

	@Override
	public void addEmail(int i, String subject) {

	}

	@Override
	public void increaseCounterOfEmailByOne(int email_id, String recipient_email) {
		add(email_id, recipient_email);
	}

	private void add(int email_id, String recipient_email) {

		NotificationDetail notification_detail = new NotificationDetail();
		notifications.add(notification_detail);
	}

	private ArrayList<NotificationDetail> getReceipentOfEmail(int email_id) {
		return notifications;
	}
}

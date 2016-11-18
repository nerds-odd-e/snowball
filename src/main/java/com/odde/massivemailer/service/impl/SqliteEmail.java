package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.NotificationService;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		while (resultSet.next()) {

			try {
				Notification mail = new Notification();

				mail.setNotificationId(resultSet.getLong("notification_id"));
				mail.setSubject(resultSet.getString("subject"));

				String sentDate = resultSet.getTimestamp("sent_at").toString();
				System.out.print("senTDate"+sentDate);
				mail.setSentDate(dateFormat.parse(sentDate));
				emailLogger.info("notifmnmkmnknknn id =" + mail.getSentDate());
				emailLogger.info("notification id =" + mail.getId());
				emailLogger.info("subject =" + mail.getSubject());
				//emailLogger.info("sent_at =" + mail.getSentDate());

				sentEmailList.add(mail);
			}catch (Exception exp) {
				exp.printStackTrace();
				emailLogger.info(exp.getMessage());
			}
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
		try {
			openConnection();
			statement.execute("DELETE FROM notification_details;");
			statement.execute("DELETE FROM notifications;");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	@Override
	public void setOpenedEmailCountList(int i) {

	}

	@Override
	public String getEmailCounterJson(Long sender_email_id) {
		ArrayList<String> sarray = new ArrayList<String>();
		int count = 0;
		for (NotificationDetail notif: getReceipentOfEmail(sender_email_id)) {
			count += notif.getRead_count();
			sarray.add(notif.toJSON());
		}
		return "{\"subject\":\"\", \"sent_at\":\"\", \"total_open_count\":"+count+", \"emails\":["+String.join(", ", sarray)+"]}";
	}

	@Override
	public void addEmail(int i, String subject) {

	}

	@Override
	public void increaseCounterOfEmailByOne(Long email_id, String recipient_email) {
		add(email_id, recipient_email);
	}

	private void add(Long email_id, String recipient_email) {

		NotificationDetail notification_detail = new NotificationDetail();
        notification_detail.setEmailAddress(recipient_email);
		notifications.add(notification_detail);
	}

	private List<NotificationDetail> getReceipentOfEmail(Long email_id) {
		NotificationService ns = new NotificationServiceSqlite();
		return ns.getNotificationDetails(email_id);
	}
}

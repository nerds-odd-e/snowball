package com.odde.massivemailer.testlibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteLibrary {

	protected String dbName = "jdbc:sqlite:/usr/share/oddemail.db";

	protected Statement statement;
	protected Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void connectDB(String url) throws ClassNotFoundException,
	SQLException {
		if (connection == null ) {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(url);
		}
	}

	protected Statement openConnection() throws ClassNotFoundException,
	SQLException {

		this.connectDB(dbName);
		statement = getStatement();
		return statement;
	}

	protected void closeConnection() {
		try {
			if( statement != null)
			{
				statement.close();
				statement = null;
			}
			if( connection != null ) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public Statement getStatement() throws SQLException {
		statement = this.connection.createStatement();
		return statement;
	}

	public String clearAllContacts() {
		String result = "";
		try {
			openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("delete from mail");
			int ret = preparedStatement.executeUpdate();
			if(ret >= 0){
				result = "success";
			}else{
				result = "fail";
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = "fail";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "fail";
		} finally {
			closeConnection();
		}
		return result;
	}
}

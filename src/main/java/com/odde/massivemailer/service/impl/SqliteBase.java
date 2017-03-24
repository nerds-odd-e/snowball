package com.odde.massivemailer.service.impl;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqliteBase {

    protected String dbName = "jdbc:sqlite:oddemail.db";

    protected Statement statement;
    protected Connection connection;

    public SqliteBase(String dbLink) {
        dbName = dbLink;
    }

    public SqliteBase() {

    }

    public Statement openConnection() throws ClassNotFoundException,
            SQLException {

        this.connectDB(dbName);
        statement = getStatement();
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public void closeConnection() {
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

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void connectDB(String url) throws ClassNotFoundException,
            SQLException {
        if (connection == null ) {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            Properties properties = sqLiteConfig.toProperties();
            properties.setProperty(SQLiteConfig.Pragma.DATE_STRING_FORMAT.pragmaName, "yyyy-MM-dd HH:mm:ss");
            connection = DriverManager.getConnection(url, properties);

        }
    }
}

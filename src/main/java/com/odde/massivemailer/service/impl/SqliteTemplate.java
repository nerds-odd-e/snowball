package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.service.TemplateService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cadet on 11/26/2015.
 */
public class SqliteTemplate implements TemplateService{

    private String dbName = "jdbc:sqlite:oddemail.db";

    private Statement statement;
    private Connection connection;

    private static String selectTemplateListSql = "SELECT * FROM TEMPLATE ORDER BY TEMPLATENAME";

    public SqliteTemplate() {
        try {
            openConnection();
            createIfNotExistTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void createIfNotExistTable() throws SQLException {

        //if(!isTableExists("mail"))
        statement
                .executeUpdate("CREATE TABLE IF NOT EXISTS Template (Id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, TemplateName VARCHAR(255) NOT NULL, Subject VARCHAR(255), Content NVARCHAR(5000))");
    }

    @Override
    public List<Template> getTemplateList() {
        ResultSet resultSet;
        List<Template> templateList = null;
        try {
            openConnection();

            resultSet = statement.executeQuery(this.selectTemplateListSql);
            templateList = populateTemplateList(resultSet);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return templateList;
    }

    private List<Template> populateTemplateList(ResultSet resultSet) throws SQLException {
        List<Template> templateList = new ArrayList<Template>();
        while (resultSet.next()) {
            Template template = new Template();
            template.setId(resultSet.getInt("Id"));
            template.setTemplateName(resultSet.getString("TemplateName"));
            template.setSubject(resultSet.getString("Subject"));
            template.setContent(resultSet.getString("Content"));

            templateList.add(template);
        }
        return templateList;
    }

    private Statement openConnection() throws ClassNotFoundException,
            SQLException {

        this.connectDB(dbName);
        statement = getStatement();
        return statement;
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
            connection = DriverManager.getConnection(url);
        }
    }

}

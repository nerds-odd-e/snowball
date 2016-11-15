package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.service.TrackingService;

import java.sql.SQLException;

/**
 * Created by csd11 on 15/11/16.
 */
public class SqliteTracking extends SqliteBase implements TrackingService
{

    public SqliteTracking() {
        try {
            openConnection();
            createIfNotExistTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateViewCount(String notificationId, String emailId) {
        System.out.println("Update for :"+ notificationId + " , " + emailId);


        /**try {
            statement.executeUpdate("Update xxxx set xxx = xxx+1 where notification_id = '" + notificationId + "' and emailId = '" + emailId + "'" );
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }**/
    }

    private void createIfNotExistTable() throws SQLException {

        //if(!isTableExists("mail"))
        //statement
        //        .executeUpdate("CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50), email VARCHAR(50) NOT NULL, lastname VARCHAR(50), company VARCHAR(50))");
    }


    @Override
    public void destroyAll() {
        try {
            openConnection();
            //statement.execute("DELETE FROM mail;");
            //statement.executeUpdate("drop table if exists Template;CREATE TABLE Template (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TemplateName VARCHAR(255) NOT NULL, Subject VARCHAR(255), Content NVARCHAR(5000))");
            //statement.executeUpdate("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
            //statement.execute("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

}

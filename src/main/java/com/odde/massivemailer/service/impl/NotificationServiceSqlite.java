package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.service.NotificationService;

public class NotificationServiceSqlite extends SqliteBase implements NotificationService {
    public NotificationServiceSqlite() {
        try {
            openConnection();
            createTableIfNotExisting();
        } catch (Exception e) {

        }
    }

    private void createTableIfNotExisting() {
        try {
            statement.executeUpdate("CREATE TABLE Template (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TemplateName VARCHAR(255) NOT NULL, Subject VARCHAR(255), Content NVARCHAR(5000))");
        } catch (Exception e)
        {

        }
    }
}

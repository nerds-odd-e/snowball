package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.service.TrackingService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteTracking extends SqliteBase implements TrackingService
{

    public SqliteTracking() {
        try {
            openConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int updateViewCount(int messageId, int userId) {

        try {

            String sql = "Update notification_details set mail_read_count = mail_read_count+1 where notification_id = ? and contact_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            preparedStatement.setInt(2, userId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return 0;
    }

}

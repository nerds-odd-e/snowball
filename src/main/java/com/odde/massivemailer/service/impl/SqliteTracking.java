package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.service.TrackingService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteTracking extends SqliteBase implements TrackingService {
    String sql = "Update notification_details set read_count = read_count+1 where notification_id = ? and email_address = ?";

    @Override
    public int updateViewCount(long messageId, String userId) {

        try {
            openConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, messageId);
            preparedStatement.setString(2, userId);

            return preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return 0;
    }

}

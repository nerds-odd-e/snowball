package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.service.TrackingService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteTracking extends SqliteBase implements TrackingService {
    String sql = "Update notification_details set read_count = read_count + 1 where notification_id = ? and email_address = ?";

    public SqliteTracking() {
        try {
            openConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int updateViewCount(int messageId, String userId) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, messageId);
            preparedStatement.setString(2, userId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return 0;
    }

}

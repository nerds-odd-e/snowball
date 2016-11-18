package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.service.TrackingService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteTracking extends SqliteBase implements TrackingService {
    @Override
    public int updateViewCount(final Long token) {
        String sql = "UPDATE notification_details SET read_count = read_count + 1 WHERE id = ?";

        try {
            openConnection();

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setLong(1, token);

            return ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return 0;
    }

}

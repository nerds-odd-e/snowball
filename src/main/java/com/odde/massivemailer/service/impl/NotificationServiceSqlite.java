package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.NotificationService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceSqlite extends SqliteBase implements NotificationService {
    private List<NotificationDetail> notificationDetailList;

    @Override
    public Notification save(final Notification notification) {
        try {
            openConnection();

            saveNotification(notification);

            for (NotificationDetail notificationDetail : notification.getNotificationDetails()) {
                saveNotificationDetail(notificationDetail, notification);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return notification;
    }

    @Override
    public List<NotificationDetail> getNotificationDetails(Long notificationId) {
        String sql = "SELECT * from notification_details where notification_id = ?";

        try {
            openConnection();
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, Long.toString(notificationId));
            ResultSet resultSet =ps.executeQuery();
            populateNotificationDetailsList(resultSet);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return notificationDetailList;
    }

    private void populateNotificationDetailsList(ResultSet resultSet) throws SQLException {
        notificationDetailList = new ArrayList<NotificationDetail>();
        while (resultSet.next()) {
            NotificationDetail notificationDetail = new NotificationDetail();
            notificationDetail.setId((long) resultSet.getInt("id"));
            notificationDetail.setRead_count(resultSet.getInt("read_count"));
            notificationDetail.setEmailAddress(resultSet.getString("email_address"));

            notificationDetailList.add(notificationDetail);
        }
    }

    private void saveNotification(final Notification notification) {
        String sql = "INSERT INTO notifications (subject, notification_id, sent_at) VALUES (?, ?, datetime('now'))";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = getConnection().prepareStatement(sql);

            ps.setString(1, notification.getSubject());
            ps.setLong(2, notification.getNotificationId());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                notification.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveNotificationDetail(final NotificationDetail notificationDetail, final Notification notification) {
        String sql = "INSERT INTO notification_details (notification_id, email_address) VALUES (?, ?)";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = getConnection().prepareStatement(sql);

            ps.setLong(1, notification.getNotificationId());
            ps.setString(2, notificationDetail.getEmailAddress());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                notificationDetail.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}

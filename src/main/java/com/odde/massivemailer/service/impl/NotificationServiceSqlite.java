package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.NotificationService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationServiceSqlite extends SqliteBase implements NotificationService {
    @Override
    public Notification save(final Notification notification) {
        Notification savedNotification = null;

        try {
            openConnection();

            savedNotification = saveNotification(notification);

            for (NotificationDetail notificationDetail : notification.getNotificationDetails()) {
                savedNotification.addNotificationDetail(saveNotificationDetail(notificationDetail, savedNotification));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return savedNotification;
    }

    private Notification saveNotification(final Notification notification) {
        String sql = "INSERT INTO notifications (subject, notification_id, sent_at) VALUES (?, ?, datetime('now'))";

        Notification savedNotification = new Notification();
        savedNotification.setSubject(notification.getSubject());
        savedNotification.setNotificationId(notification.getNotificationId());

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = getConnection().prepareStatement(sql);

            ps.setString(1, notification.getSubject());
            ps.setLong(2, notification.getNotificationId());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                savedNotification.setId(rs.getLong(1));
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

        return savedNotification;
    }

    private NotificationDetail saveNotificationDetail(final NotificationDetail notificationDetail,
                                                      final Notification notification) {
        String sql = "INSERT INTO notification_details (notification_id, email_address) VALUES (?, ?)";

        NotificationDetail savedNotificationDetail = new NotificationDetail();
        savedNotificationDetail.setEmailAddress(notificationDetail.getEmailAddress());

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = getConnection().prepareStatement(sql);

            ps.setLong(1, notification.getId());
            ps.setString(2, notificationDetail.getEmailAddress());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                savedNotificationDetail.setId(rs.getLong(1));
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

        return savedNotificationDetail;
    }
}

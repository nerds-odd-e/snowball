package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.NotificationService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationServiceSqlite extends SqliteBase implements NotificationService {
    private ArrayList<NotificationDetail> notifications;

    public NotificationServiceSqlite() {
        notifications = new ArrayList<NotificationDetail>();
    }

    @Override
    public Notification save(final Notification notification) {
        PreparedStatement ps = null;

        try {
            openConnection();

            String sql = "INSERT INTO notifications (subject, notification_id, sent_at) VALUES (?, ?, datetime('now'))";

            ps = getConnection().prepareStatement(sql);

            ps.setString(1, notification.getSubject());
            ps.setLong(2, notification.getNotificationId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                notification.setId(rs.getLong(1));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            closeConnection();
        }

        return notification;
    }

    @Override
    public void add(int email_id, String recipient_email) {

        NotificationDetail notification_detail = new NotificationDetail();
        notifications.add(notification_detail);
    }

    @Override
    public ArrayList<NotificationDetail> getReceipentOfEmail(int email_id) {
        return notifications;
    }
}

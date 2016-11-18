package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.NotificationService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public Notification getNotification(Long id) {
        String sql = "SELECT notification_id, subject, sent_at FROM notifications WHERE notification_id = ?";
        Notification noti = null;
        try {
            openConnection();
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, Long.toString(id));
            ResultSet resultSet = ps.executeQuery();
            noti = populateNotification(resultSet);
            noti.setNotificationDetails(getNotificationDetails(id));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return noti;
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

    private Notification populateNotification(ResultSet resultSet) throws SQLException {
        Notification noti = new Notification();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (resultSet.next()) {
            noti.setId(resultSet.getLong("notification_id"));
            noti.setSubject(resultSet.getString("subject"));
            String sentDate = resultSet.getTimestamp("sent_at").toString();
            try {
                noti.setSentDate(dateFormat.parse(sentDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            noti.setNotificationId((long) resultSet.getInt("notification_id"));
            break;
        }
        return noti;
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
        String sql = "INSERT INTO notifications (subject, notification_id, sent_at) VALUES (?, ?, ?)";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = getConnection().prepareStatement(sql);

            ps.setString(1, notification.getSubject());
            ps.setLong(2, notification.getNotificationId());
            java.util.Date sentDate = notification.getSentDate() != null ? notification.getSentDate() : new java.util.Date();
            ps.setDate(3, new Date(sentDate.getTime()));

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

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO notification_details (notification_id, email_address, read_count) VALUES (?, ?, ?)";
            ps = getConnection().prepareStatement(sql);

            ps.setLong(1, notification.getNotificationId());
            ps.setString(2, notificationDetail.getEmailAddress());
            ps.setInt(3, notificationDetail.getRead_count());

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

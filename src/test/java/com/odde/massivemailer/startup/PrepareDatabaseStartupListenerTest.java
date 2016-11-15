package com.odde.massivemailer.startup;

import com.odde.massivemailer.service.impl.SqliteBase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContextListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrepareDatabaseStartupListenerTest {
    private ServletContextListener listener;
    private SqliteBase base;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        listener = new PrepareDatabaseStartupListener();
        base = new SqliteBase();

        base.openConnection();
    }

    @After
    public void tearDown() {
        base.closeConnection();
    }

    @Test
    public void MailTableMustExist() throws SQLException {
        listener.contextInitialized(null);

        assertTableExists("mail");
    }

    @Test
    public void TemplateTableMustExist() throws SQLException {
        listener.contextInitialized(null);

        assertTableExists("Template");
    }

    @Test
    public void NotificationsTableMustExist() throws SQLException {
        listener.contextInitialized(null);

        assertTableExists("notifications");
    }

    @Test
    public void NotificationDetailsTableMustExist() throws SQLException {
        listener.contextInitialized(null);

        assertTableExists("notification_details");
    }

    private void assertTableExists(String tableName) throws SQLException {
        String query = "SELECT COUNT(1) FROM " + tableName;

        ResultSet rs = base.getStatement().executeQuery(query);

        Assert.assertTrue(rs.getInt(1) >= 0);
    }
}

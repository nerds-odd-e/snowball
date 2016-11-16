package com.odde.massivemailer.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SqliteTrackingTest {

    private SqliteTracking sqliteTracking;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Connection mockConnection;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        sqliteTracking = new SqliteTracking();
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockConnection.prepareStatement(Matchers.anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(null);
        sqliteTracking.setConnection(mockConnection);
    }

    @Test
    public void updateViewCountByOne() throws SQLException {
        int result= sqliteTracking.updateViewCount(100900, "terry@gmail.com");
        verify(mockPreparedStatement).executeUpdate();
        assertEquals(1, result);
    }
    @Test
    public void ensureIncrement() throws SQLException {
        assertTrue(sqliteTracking.sql.replaceAll(" ", "").contains("read_count+1"));
    }
}

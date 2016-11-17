package com.odde.massivemailer.service.impl;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.javalite.db_migrator.DbUtils.closeConnection;

public class EmailCounterTracking {
    String sql = "Select email_address, email_count from notification_details where notification_id = ?";


    public int selectViewCount(long messageId, String userId) {

              return 1;
    }
}

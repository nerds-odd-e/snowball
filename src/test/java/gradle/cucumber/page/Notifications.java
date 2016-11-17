package gradle.cucumber.page;

import com.odde.massivemailer.service.impl.SqliteBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.fail;

public class Notifications {
    public static int getNotificationDetailCount(String receipient) throws ClassNotFoundException, SQLException {
        SqliteBase base = new SqliteBase();

        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            base.openConnection();

            String sql = "SELECT read_count FROM notification_details WHERE email_address = ?";

            ps = base.getConnection().prepareStatement(sql);
            ps.setString(1, receipient);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                fail("No result?");
            }
        } finally {
            rs.close();
            ps.close();
            base.closeConnection();
        }
        return count;
    }
}

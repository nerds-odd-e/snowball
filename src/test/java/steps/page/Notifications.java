package steps.page;

import com.odde.massivemailer.model.NotificationDetail;

import java.sql.SQLException;

import static org.junit.Assert.fail;

public class Notifications {
    public static int getNotificationDetailCount(String receipient) throws ClassNotFoundException, SQLException {
        NotificationDetail nd = NotificationDetail.first("email_address = ?", receipient);
        return nd.getRead_count();
    }
}

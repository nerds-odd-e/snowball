package steps.page;

import com.odde.massivemailer.model.SentMailVisit;

import java.sql.SQLException;

import static org.junit.Assert.fail;

public class Notifications {
    public static int getSentMailVisitCount(String receipient) throws ClassNotFoundException, SQLException {
        SentMailVisit nd = SentMailVisit.first("email_address = ?", receipient);
        return nd.getReadCount();
    }
}

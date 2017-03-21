package steps.page;

import com.odde.massivemailer.service.impl.SqliteBase;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.fail;

public class ImagePage {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public void load(final String recipient) throws SQLException, ClassNotFoundException {
        SqliteBase base = new SqliteBase();

        PreparedStatement ps = null;
        ResultSet rs = null;

        Long token = 0L;

        try {
            base.openConnection();

            String sql = "SELECT id FROM notification_details WHERE email_address = ?";

            ps = base.getConnection().prepareStatement(sql);
            ps.setString(1, recipient);
            rs = ps.executeQuery();

            if (rs.next()) {
                token = rs.getLong(1);
            } else {
                fail("No result?");
            }
        } finally {
            rs.close();
            ps.close();
            base.closeConnection();
        }

        String url = BASE_URL+"resources/images/qrcode.png?token=" + token;
        driver.visit(url);
    }
}

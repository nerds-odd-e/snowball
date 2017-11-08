package steps.page;

import com.odde.massivemailer.model.SentMailVisit;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import java.sql.SQLException;

public class ImagePage {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public void load(final String recipient) throws SQLException, ClassNotFoundException {
        String token = SentMailVisit.first("email_address = ?", recipient).getString("id");
        String url = BASE_URL+"resources/images/qrcode.png?token=" + token;
        driver.visit(url);
    }
}

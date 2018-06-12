package steps.page;

import com.odde.massivemailer.model.SentMailVisit;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import java.sql.SQLException;

public class ImagePage {
    private MassiveMailerSite site;

    public ImagePage(MassiveMailerSite site) {
        this.site = site;
    }

    public void load(final String recipient) throws SQLException, ClassNotFoundException {
        String token = SentMailVisit.first("email_address = ?", recipient).getString("id");
        String path = "resources/images/qrcode.png?token=" + token;
        site.visit(path);
    }
}

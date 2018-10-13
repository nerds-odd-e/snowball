package steps.site.pages;

import com.odde.massivemailer.model.SentMailVisit;
import steps.site.MassiveMailerSite;

import java.sql.SQLException;

public class ImagePage {
    private MassiveMailerSite site;

    public ImagePage(MassiveMailerSite site) {
        this.site = site;
    }

    public void load(final String recipient) {
        String token = SentMailVisit.first("email_address = ?", recipient).getString("id");
        String path = "resources/images/qrcode.png?token=" + token;
        site.visit(path);
    }
}

package steps.site.pages;

import com.odde.massivemailer.model.SentMailVisit;
import steps.site.MassiveMailerSite;

public class ImagePage {
    private final MassiveMailerSite site;

    public ImagePage(MassiveMailerSite site) {
        this.site = site;
    }

    public void load(final String recipient) {
        String token = SentMailVisit.repository().findFirstBy("emailAddress", recipient).getStringId();
        String path = "resources/images/qrcode.png?token=" + token;
        site.visit(path);
    }
}

package cucumber.steps.site.pages;

import com.odde.snowball.model.SentMailVisit;
import cucumber.steps.site.SnowballSite;

import static com.odde.snowball.model.base.Repository.repo;

public class ImagePage {
    private final SnowballSite site;

    public ImagePage(SnowballSite site) {
        this.site = site;
    }

    public void load(final String recipient) {
        String token = repo(SentMailVisit.class).findFirstBy("emailAddress", recipient).getStringId();
        String path = "resources/images/qrcode.png?token=" + token;
        site.visit(path);
    }
}

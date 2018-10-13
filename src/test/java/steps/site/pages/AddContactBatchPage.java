package steps.site.pages;

import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class AddContactBatchPage {
    private MassiveMailerSite site;
    private WebDriverWrapper driver;

    public AddContactBatchPage(MassiveMailerSite massiveMailerSite) {
        site = massiveMailerSite;
        this.driver = site.getDriver();
    }

}

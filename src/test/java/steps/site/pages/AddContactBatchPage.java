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

    public void addContactBatch() {
        site.visit("add_contact_batch.jsp");
        driver.clickButton("batchFile");
    }
}

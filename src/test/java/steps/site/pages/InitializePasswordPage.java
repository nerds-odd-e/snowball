package steps.site.pages;

import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class InitializePasswordPage {

    private final WebDriverWrapper driver;

    public InitializePasswordPage(MassiveMailerSite site) {
        this.driver = site.getDriver();
    }

    public void setPassword(final String password) {
        driver.setTextField("password", password);
    }

    public void setPasswordConfirm(final String password) {
        driver.setTextField("password_confirm", password);
    }

    public void submit() {
        driver.clickButton("submit");
    }
}

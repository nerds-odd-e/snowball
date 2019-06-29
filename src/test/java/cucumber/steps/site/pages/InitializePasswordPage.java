package cucumber.steps.site.pages;

import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class InitializePasswordPage {

    private final WebDriverWrapper driver;

    public InitializePasswordPage(SnowballSite site) {
        this.driver = site.getDriver();
    }

    public void setPassword(final String password) {
        driver.setTextField("password", password);
    }

    public void setPasswordConfirm(final String password) {
        driver.setTextField("password_confirm", password);
    }

    public void submit() {
        driver.click("#submit");
    }
}

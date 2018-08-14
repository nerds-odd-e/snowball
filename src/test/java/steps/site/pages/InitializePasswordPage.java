package steps.site.pages;

import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.sql.SQLException;

public class InitializePasswordPage {

    private WebDriverWrapper driver;

    public InitializePasswordPage(MassiveMailerSite site) {
        this.driver = site.getDriver();
        site.visit("initial_password.jsp");
    }

    public void setPassword(final String password) throws SQLException, ClassNotFoundException {
        driver.setTextField("password", password);
        driver.setTextField("password_confirm", password);
        driver.clickButton("submit");
    }
}

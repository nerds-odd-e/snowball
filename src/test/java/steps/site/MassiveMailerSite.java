package steps.site;

import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class MassiveMailerSite {
    public final WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public String baseUrl() {
        return "http://localhost:8060";
    }

    public WebDriverWrapper getDriver() {
        return driver;
    }

    public void visit(String path) {
        driver.visit(baseUrl() + path);
    }


}

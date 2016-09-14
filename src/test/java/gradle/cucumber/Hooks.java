package gradle.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Hooks {
    private WebDriver driver = SingleDrive.getDriver();

    @Before
    public void beforeScenario() {
    }

    @After
    public void afterScenario() {
        driver.close();
    }
}

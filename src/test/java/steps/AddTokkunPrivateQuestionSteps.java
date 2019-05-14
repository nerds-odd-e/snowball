package steps;

import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddTokkunPrivateQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^チェックボックスが表示される$")
    public void チェックボックスが表示される() throws Throwable {
        site.visit("onlinetest/add_question.jsp");
        assertEquals("Add Question", driver.getCurrentTitle());
        assertEquals("1", driver.findElements(By.cssSelector("#is-public")).get(0).getAttribute("value"));
    }

}
package steps;

import cucumber.api.java.en.Given;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class AddTokkunPrivateQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^ユーザーがログインしている$")
    public void ユーザーがログインしている() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Given("^ユーザーが特訓のAdd Tokkun Question pageを開いている$")
    public void ユーザーが特訓のadd_Tokkun_Question_pageを開いている() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }
}
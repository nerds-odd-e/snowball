package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.UiElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;

public class AdviceSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @Given("^I take the test$")
    public void i_take_the_test() throws Throwable {
        // TODO create test session
    }

    @Given("^there are \"([^\"]*)\" questions remaining$")
    public void there_are_questions_remaining(String qnsLeft) throws Throwable {
        // TODO add question to test session
    }

    @Given("^I'm on Advice Page$")
    public void i_m_on_Advice_Page() throws Throwable {
        site.visit("question");
        UiElement option1 = driver.findElementById("option1");
        option1.click();
        UiElement nextButton = driver.findElementById("answer");
        nextButton.click();
    }

    @When("^I click on the Next button$")
    public void i_click_on_the_button() throws Throwable {
        UiElement nextButton = driver.findElementById("next");
        nextButton.click();
    }

    @Then("^I should see the \"([^\"]*)\" page$")
    public void i_should_see_the_page(String expectedPageTitle) throws Throwable {
        String title = driver.getCurrentTitle();
        assertEquals(expectedPageTitle, title);
    }
}

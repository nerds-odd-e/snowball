package steps;

import cucumber.api.PendingException;
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
    public void i_take_the_test() {
        site.visit("question");
        // TODO create test session
    }

    @Given("^there are \"([^\"]*)\" questions remaining$")
    public void there_are_questions_remaining(String qnsLeft) {
        // TODO add question to test session
    }

    @Given("^I'm on Advice Page$")
    public void i_m_on_Advice_Page() {
        UiElement option1 = driver.findElementById("option1");
        option1.click();
        UiElement nextButton = driver.findElementById("answer");
        nextButton.click();
    }

    @When("^I click on the Next button$")
    public void i_click_on_the_button() {
        UiElement nextButton = driver.findElementById("next");
        nextButton.click();
    }

    @Then("^I should see the \"([^\"]*)\" page$")
    public void i_should_see_the_page(String expectedPageTitle) {
        String title = driver.getCurrentTitle();
        assertEquals(expectedPageTitle, title);
    }

    @Given("^User is in the Question page$")
    public void userIsInTheQuestionPage() {
        site.visit("launchQuestion");
    }

    @When("^User selects the wrong option$")
    public void userSelectsTheWrongOption() {
        UiElement wrongOption = driver.findElementById("option1");
        wrongOption.click();
    }

    @When("^User answers the question wrongly$")
    public void userAnswersTheQuestionWrongly() {
        UiElement nextButton = driver.findElementById("answer");
        nextButton.click();
    }

    @Then("^User should go to the Advice page$")
    public void userShouldGoToTheAdvicePage() {
        String title = driver.getCurrentTitle();
        assertEquals("Question", title);
    }
}

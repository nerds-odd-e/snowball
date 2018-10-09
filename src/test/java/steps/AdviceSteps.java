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
        // TODO Create test session here
    }

    @Given("^there are \"([^\"]*)\" questions$")
    public void there_are_questions(String moreQuestions) throws Throwable {
        boolean hasMoreQuestions = Boolean.parseBoolean(moreQuestions);
        // TODO insert multiple questions into current test session if hasMoreQuestions is true
        // Otherwise insert only one question
    }

    @Given("^I'm on Advice Page$")
    public void i_m_on_Advice_Page() throws Throwable {
        site.visit("advice.jsp");
        assertEquals("Advice", driver.findElementByName("title").getText());
    }

    @When("^I click on the \"([^\"]*)\" button$")
    public void i_click_on_the_button(String buttonName) throws Throwable {
        UiElement nextButton = driver.findElementById("next");
        assertEquals(buttonName, nextButton.getText());
        nextButton.click();
    }

    @Then("^I should see the \"([^\"]*)\" page$")
    public void i_should_see_the_page(String expectedTitle) throws Throwable {
        String title = driver.findElementByName("title").getText();
        assertEquals(expectedTitle, title);
    }
}

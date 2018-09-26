package steps.onlinetest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StudentScoreSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();


    @Given("^There are (\\d+) questions and (\\d+) points$")
    public void there_are_questions_and_points(int questions, int points) throws Throwable {
        assertTrue(points <= questions);
    }

    @When("^Test is finished$")
    public void test_is_finished() throws Throwable {
        site.visit("score");
    }

    @Then("^Display the message \"([^\"]*)\"$")
    public void display_the_message(String message) throws Throwable {
        site.visit("score.jsp");
        final WebElement scoreMessage = driver.findElements(By.className("score-message")).get(0);
        assertEquals(message, scoreMessage.getText());
    }

    @Then("^The percent is \"([^\"]*)\"$")
    public void the_percent_is(String message) throws Throwable {
        site.visit("score.jsp");
        final WebElement scoreMessage = driver.findElements(By.className("percent")).get(0);
        assertEquals(message, scoreMessage.getText());
    }

    @Then("^The fraction is \"([^\"]*)\"$")
    public void the_fraction_is(String message) throws Throwable {
        site.visit("score.jsp");
        final WebElement scoreMessage = driver.findElements(By.className("fraction")).get(0);
        assertEquals(message, scoreMessage.getText());
    }
}


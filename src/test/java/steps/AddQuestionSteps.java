package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.UiElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddQuestionSteps {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    // After make a question the form is reset
    @Given("^no question registered$")
    public void no_question_registered() throws Throwable {
        site.addQuestionPage();
    }

    @When("^Push submit with required fields$")
    public void push_submit_with_required_fields() throws Throwable {
        site.visit("add_question.jsp");
        driver.setTextField("question_body", "body");
        driver.setTextField("answer_1", "answer_1");
        driver.setTextField("answer_2", "answer_2");
        driver.clickButton("save_button");
    }

    @Then("^Display registered contents$")
    public void display_registered_contents() throws Throwable {
        final WebElement question = driver.findElements(By.className("question")).get(0);
        assertEquals("body", question.findElement(By.className("question_body")).getText());
        final WebElement answers = question.findElement(By.className("answers"));
        final List<WebElement> answerList = answers.findElements(By.className("answer"));
        assertEquals("answer_1", answerList.get(0).getText());
        assertEquals("answer_2", answerList.get(1).getText());
    }

    @Then("^Reset form$")
    public void reset_form() throws Throwable {
        final WebElement form = driver.findElements(By.tagName("form")).get(0);
        assertEquals("", form.findElement(By.name("question_body")).getText());
    }

    // I can set the question fields
    @Given("^there are no questions$")
    public void there_are_no_questions() throws Throwable {
        site.addQuestionPage();
    }

    @When("^load the form$")
    public void load_the_form() throws Throwable {
        site.visit("add_question.jsp");
        final WebElement form = driver.findElements(By.tagName("form")).get(0);
        final WebElement questionBody = form.findElement(By.name("question_body"));
        questionBody.sendKeys("body");
    }

    @Then("^I can input the question_body$")
    public void i_can_input_the_question_body() throws Throwable {
        final WebElement form = driver.findElements(By.tagName("form")).get(0);
        assertEquals("body", form.findElement(By.name("question_body")).getAttribute("value"));
    }

    @Then("^Click the save button$")
    public void click_the_save_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}

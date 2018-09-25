package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class AddQuestionSteps {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

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
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Reset form$")
    public void reset_form() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}

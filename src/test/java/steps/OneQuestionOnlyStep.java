package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class OneQuestionOnlyStep {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @Given("^User is student$")
    public void user_is_student() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User clicks \"([^\"]*)\" button$")
    public void user_clicks_button(String arg1) throws Throwable {
        site.visit("question");
    }

    @Then("^User go to the test page$")
    public void user_go_to_the_test_page() throws Throwable {
        driver.expectElementWithIdToContainText("description", "What is scrum?");
    }

    @Then("^User should see a question and options$")
    public void user_should_see_a_question_and_options() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User is in the test page$")
    public void user_is_in_the_test_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User chooses the correct option$")
    public void user_chooses_the_correct_option() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks the answer button$")
    public void user_clicks_the_answer_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User should see the \"([^\"]*)\" page$")
    public void user_should_see_the_page(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User chooses incorrect option$")
    public void user_chooses_incorrect_option() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User should see the selected option highlighted in red$")
    public void user_should_see_the_selected_option_highlighted_in_red() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The correct option should be highlighted in green$")
    public void the_correct_option_should_be_highlighted_in_green() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User should see the advice$")
    public void user_should_see_the_advice() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User is in advice page$")
    public void user_is_in_advice_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks next button$")
    public void user_clicks_next_button() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Student as user$")
    public void student_as_user() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Student want to take random test$")
    public void student_want_to_take_random_test() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^System displayed random test question page\\.$")
    public void system_displayed_random_test_question_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Student pressed \"([^\"]*)\" button\\.$")
    public void student_pressed_button(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a trainer enters the question edit page$")
    public void aTrainerEntersTheQuestionEditPage() throws Throwable {
        site.visit("add_question.jsp");
    }

    @When("^he add a question description \"([^\"]*)\"$")
    public void heAddAQuestionDescription(String description) throws Throwable {
        driver.setTextField("description", description);
    }

    @And("^add question option(\\d+) \"([^\"]*)\"$")
    public void addQuestionOption(int option_id, String text) throws Throwable {
        driver.setTextField("option" + option_id, text);
    }

    @And("^he set the option(\\d+) as the correct answer$")
    public void heSetTheOptionAsTheCorrectAnswer(int option_id) throws Throwable {
        driver.clickById("option" + option_id);
    }

    @And("^he set the advise \"([^\"]*)\"$")
    public void heSetTheAdvise(String advice) throws Throwable {
        driver.setTextField("advice", advice);
    }

    @And("^he press the \"([^\"]*)\"$")
    public void hePressThe(String buttonName) throws Throwable {
        driver.clickButtonByName(buttonName);
    }

    @And("^he enters the question list page$")
    public void heEntersTheQuestionListPage() throws Throwable {
        site.visit("question_list.jsp");
    }
}

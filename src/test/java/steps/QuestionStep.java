package steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.Map;

public class QuestionStep {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @When("^User clicks \"([^\"]*)\" button$")
    public void user_clicks_button(String arg1) {
        site.visit("question");
    }

    @Then("^User go to the test page$")
    public void user_go_to_the_test_page() {
        driver.expectElementWithIdToContainText("description", "What is scrum?");
    }

    @Then("^User should see a question and options$")
    public void user_should_see_a_question_and_options(DataTable question) {
        Map<String, String> questionMap = question.asMap(String.class, String.class);
        driver.expectElementWithIdToContainText("description", questionMap.get("description"));
        driver.expectElementWithIdToContainText("option1", questionMap.get("option1"));
        driver.expectElementWithIdToContainText("option2", questionMap.get("option2"));
        driver.expectElementWithIdToContainText("option3", questionMap.get("option3"));
        driver.expectElementWithIdToContainText("option4", questionMap.get("option4"));
        driver.expectElementWithIdToContainText("option5", questionMap.get("option5"));
    }

    @Given("^User is in the test page$")
    public void user_is_in_the_test_page() {
        site.visit("question");
    }

    @When("^User chooses the correct option$")
    public void user_chooses_the_correct_option() {
        driver.clickById("option5");
    }

    @When("^User clicks the answer button$")
    public void user_clicks_the_answer_button() {
        driver.clickButton("answer");
    }

    @Then("^User should see the \"([^\"]*)\" page$")
    public void user_should_see_the_page(String pageName) throws Throwable {
        driver.expectElementWithIdToContainText("title", "End Of Test");
    }

    @When("^User chooses the incorrect option$")
    public void user_chooses_the_incorrect_option() {
        driver.clickById("option2");
    }

    @Then("^User go to the \"([^\"]*)\" page$")
    public void user_go_to_the_page(String pageName) {
        driver.pageShouldContain(pageName);
    }

    @Then("^User should see correct option highlighted in \"([^\"]*)\"$")
    public void user_should_see_correct_option_highlighted_in(String arg1) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User should see selected incorrect option highlighted in \"([^\"]*)\"$")
    public void user_should_see_selected_incorrect_option_highlighted_in(String arg1) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User should see \"([^\"]*)\"$")
    public void user_should_see(String arg1) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a trainer enters the question edit page$")
    public void aTrainerEntersTheQuestionEditPage() {
        driver.visit(site.baseUrl() + "add_question.jsp");
    }

    private void addQuestionWithDumyData() {
        driver.setTextField("description", "dumy description");
        driver.setTextField("option1", "dumy option1");
        driver.setTextField("option2", "dumy option2");
        driver.setTextField("advice", "dumy advice");
    }

    @When("^trainer add a new question with advice that have \"([^\"]*)\"$")
    public void trainerAddANewQuestionWithAdviceThatHave(int description_length) {
        this.addQuestionWithDumyData();
        driver.setTextField("description", getStringOfLength(description_length));
    }

    private String getStringOfLength(int description_length) {
        String description = "";
        for (int i = 0; i < description_length; i++) {
            description += "a";
        }
        return description;
    }
}

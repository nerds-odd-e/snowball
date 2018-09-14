package steps;

import com.odde.massivemailer.controller.QuestionController;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.javalite.activejdbc.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionStep {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @Given("^User is in the test page$")
    public void user_is_in_the_test_page() {
        setup();
        site.visit("question");
    }

    private void setup() {
        String csv_path = getClass().getResource("/csv/questions.csv").getPath();
        String query = String.format("LOAD DATA INFILE '%s' INTO TABLE questions " +
                "FIELDS TERMINATED BY ',' ENCLOSED BY '\"' (description, is_multi_question, advice);", csv_path);
        Base.exec(query);

    }

    @Given("^User is in the top page$")
    public void user_is_in_the_top_page() throws Throwable {
        site.visit("");
    }

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

    @When("^User chooses \"([^\"]*)\"$")
    public void user_chooses(String selected_option) {
        driver.clickById(selected_option);
    }

    @When("^User clicks the answer button$")
    public void user_clicks_the_answer_button() {
        driver.clickButton("answer");
    }

    @Then("^Move to \"([^\"]*)\" page$")
    public void move_to_page(String redirected_page) {
        driver.pageShouldContain(redirected_page);
    }

    @When("^User chooses the correct option$")
    public void user_chooses_the_correct_option() {
        driver.clickById("option5");
    }

    @Then("^User should see the \"([^\"]*)\" page$")
    public void user_should_see_the_page(String pageName) {
        driver.expectElementWithIdToContainText("title", "End Of Test");
    }

    @When("^User chooses the \"([^\"]*)\" option$")
    public void user_chooses_the_option(String incorrectId) throws Throwable {
        driver.clickById(incorrectId);
    }

    @Then("^User go to the \"([^\"]*)\" page$")
    public void user_go_to_the_page(String pageName) {
        driver.pageShouldContain(pageName);
    }

    @Then("^User should see \"([^\"]*)\" option highlighted and text \"([^\"]*)\"$")
    public void user_should_see_option_highlighted_and_text(String clazz, String text) {
        String cssSelector = "." + clazz.replace(" ", ".");
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        assertEquals(elements.size(), 1);
        assertEquals(elements.get(0).getText(), text);
    }

    @Then("^User should see \"([^\"]*)\"$")
    public void user_should_see(String text) {
        driver.expectElementWithIdToContainValue("advice", text);
    }

    @When("^option(\\d+) is selected as correct answer$")
    public void optionIsSelectedAsCorrectAnswer(String optionId) {
        driver.clickButton("option"+optionId );
    }

    @Given("^User arrives at advice page$")
    public void user_arrives_at_advice_page() {
        site.visit("question");
        driver.clickById("option1");
        driver.clickButton("answer");
    }

    @When("^User clicks the next button$")
    public void user_clicks_the_next_button() {
        driver.clickButton("next");
    }

    @Given("^There is a question \"([^\"]*)\"$")
    public void there_is_a_question(String arg1) {
    }

    @When("^User clicks \"([^\"]*)\" button on menu$")
    public void user_clicks_button_on_menu(String link) {
        driver.clickById("start_test");
    }

    @Given("^User answered \"([^\"]*)\" times in the test page$")
    public void user_answered_times_in_the_test_page(String answeredCount) {
        driver.expectElementWithIdToContainText("answeredCount", answeredCount);
        driver.pageShouldContain("Question");
    }

    @Then("^Move to next question page$")
    public void move_to_next_question_page() {
        driver.pageShouldContain("Question");
    }

    @Given("^There are (\\d+) questions$")
    public void there_are_questions(int questionCount) throws Throwable {
        assertEquals(questionCount, QuestionController.MAX_QUESTION_COUNT);
    }

    @Given("^User answered correctly the (\\d+) th question page$")
    public void user_answered_correctly_the(int answeredCount) throws Throwable {
        for (int i = 0; i < answeredCount; ++i) {
            driver.clickById("option5");
            driver.clickButton("answer");
        }
    }

    @Then("^\"([^\"]*)\" is shown$")
    public void is_shown(String currentPage) throws Throwable {
        driver.pageShouldContain(currentPage);
    }

    @Given("^There are \"([^\"]*)\" questions remaining$")
    public void there_are_questions_remaining(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}

package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.AnswerOption;
import com.odde.massivemailer.model.Question;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
    private Question question;

    @Given("^User is taking a quiz with 2 questions$")
    public void user_is_taking_a_quiz_with_2_questions() {
        for(int i = 0; i < 2; i ++)
            question = new QuestionBuilder()
                    .aQuestion()
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();
    }

    @Given("^User is on the first question$")
    public void user_is_in_the_test_page() {
        site.visit("launchQuestion");
    }

    @Given("^User is in the top page$")
    public void user_is_in_the_top_page() {
        Question question = Question.createIt("description", "MyTest", "advice", "eeee");
        AnswerOption option1 = new AnswerOption("wrongOption", question.getLongId(), false);
        option1.saveIt();
        AnswerOption option2 = new AnswerOption("correctOption", question.getLongId(), true);
        option2.saveIt();
        site.visit("");
    }

    @Given("^User arrives at advice page$")
    public void user_arrives_at_advice_page() {
        site.visit("question");
        driver.clickById("wrongOption");
        driver.clickButton("answer");
    }

    @Given("^User answered \"([^\"]*)\" times in the test page$")
    public void user_answered_times_in_the_test_page(String answeredCount) {
        driver.expectElementWithIdToContainText("answeredCount", answeredCount);
        driver.pageShouldContain("Question");
    }

    @Given("^User answered correctly the (\\d+) th question page$")
    public void user_answered_correctly_the(int answeredCount) {
        for (int i = 0; i < answeredCount; ++i) {
            driver.clickById("option5");
            driver.clickButton("answer");
        }
    }

    @Given("^There are \"([^\"]*)\" questions remaining$")
    public void there_are_questions_remaining(String arg1) {
        // Write code here that turns the phrase above into concrete actionsthrow new Exception();
    }

    @Given("^There is only one question$")
    public void there_is_only_one_question() {
        // Write code here that turns the phrase above into concrete actions

    }

    @Given("^There are two questions$")
    public void there_are_two_questions()  {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^User clicks \"([^\"]*)\" button$")
    public void user_clicks_button(String arg1) {
        site.visit("question");
    }

    @When("^User chooses the \"([^\"]*)\" answer$")
    public void userChoosesTheAnswer(String answer) {
        driver.clickRadioButton(answer);
    }

    @When("^User clicks the answer button$")
    public void user_clicks_the_answer_button() {
        driver.clickButton("answer");
    }

    @When("^User chooses the correct option$")
    public void user_chooses_the_correct_option() {
        driver.clickById("option5");
    }

    @When("^User chooses the \"([^\"]*)\" option$")
    public void user_chooses_the_option(String incorrectId) {
        driver.clickById(incorrectId);
    }

    @When("^option(\\d+) is selected as correct answer$")
    public void optionIsSelectedAsCorrectAnswer(String optionId) {
        driver.clickButton("option" + optionId);
    }

    @When("^User clicks the next button$")
    public void user_clicks_the_next_button() {
        driver.clickButton("next");
    }

    @When("^User clicks \"([^\"]*)\" button on menu$")
    public void user_clicks_button_on_menu(String link) {
        driver.clickById("start_test");
    }

    @When("^User selects the correct answer$")
    public void user_selects_the_correct_answer() {
        // Write code here that turns the phrase above into concrete actions

    }

    @When("^User Clicks on the next Button$")
    public void user_Clicks_on_the_next_Button() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^User Clicks on the Next Button in the first question$")
    public void user_Clicks_on_the_Next_Button_in_the_first_question() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^User go to the test page$")
    public void user_go_to_the_test_page() {
        driver.expectElementWithIdToContainText("description", question.getDescription());
    }

    @Then("^User should see a question and options$")
    public void user_should_see_a_question_and_options(DataTable question) {
        Map<String, String> questionMap = question.asMap(String.class, String.class);
        driver.expectElementWithIdToContainText("description", questionMap.get("description"));
        driver.expectElementWithIdToContainText("wrongOption", questionMap.get("wrongOption"));
        driver.expectElementWithIdToContainText("option2", questionMap.get("option2"));
        driver.expectElementWithIdToContainText("option3", questionMap.get("option3"));
        driver.expectElementWithIdToContainText("option4", questionMap.get("option4"));
        driver.expectElementWithIdToContainText("option5", questionMap.get("option5"));
    }

    @Then("^It should move to \"([^\"]*)\" page$")
    public void move_to_page(String redirected_page) {
        driver.pageShouldContain(redirected_page);
    }

    @Then("^User should see the \"([^\"]*)\" page$")
    public void user_should_see_the_page(String pageName) {
        driver.expectElementWithIdToContainText("title", "End Of Test");
    }

    @Then("^User go to the \"([^\"]*)\" page$")
    public void user_go_to_the_page(String pageName) {
        driver.pageShouldContain(pageName);
    }

    @Then("^User should see \"([^\"]*)\" option highlighted and text \"([^\"]*)\"$")
    public void user_should_see_option_highlighted_and_text(String clazz, String text) {
        String cssSelector = "." + clazz.replace(" ", ".");
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        assertEquals(1, elements.size());
        assertEquals(elements.get(0).getText(), text);
    }

    @Then("^User should see \"([^\"]*)\"$")
    public void user_should_see(String text) {
        driver.expectElementWithIdToContainValue("advice", text);
    }

    @Then("^Move to next question page$")
    public void move_to_next_question_page() {
        driver.pageShouldContain("Question");
    }

    @Then("^\"([^\"]*)\" is shown$")
    public void is_shown(String currentPage) {
        driver.pageShouldContain(currentPage);
    }

    @Then("^User sees the Summary Page$")
    public void user_sees_the_Summary_Page() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^User should see second question$")
    public void user_should_see_second_question() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Given("^There are (\\d+)  questions at the beginning$")
    public void there_is_a_question(int numberOfQuestions) {
        for (int i = 0; i < numberOfQuestions; i++) {
            Question question = Question.createIt("description", "some description " + i, "advice", "adv1");
            Long id = (Long) question.getId();
            AnswerOption answerOption1 = AnswerOption.createIt("description", "Scrum is Rugby", "question_id", id, "is_correct", 0);
            AnswerOption answerOption2 = AnswerOption.createIt("description", "Scrum is Baseball", "question_id", id, "is_correct", 0);
            AnswerOption answerOption3 = AnswerOption.createIt("description", "Scrum is Soccer", "question_id", id, "is_correct", 0);
            AnswerOption answerOption4 = AnswerOption.createIt("description", "Scrum is Sumo", "question_id", id, "is_correct", 0);
            AnswerOption answerOptiom5 = AnswerOption.createIt("description", "None of the above", "question_id", id, "is_correct", 1);
        }
    }


    @Then("^User go to the test page for first time$")
    public void user_go_to_the_test_page_for_first_time() {
        site.visit("launchQuestion");
    }

    @Then("^There is a question with options")
    public void there_is_a_question_with_options() {
        assertEquals(1, driver.findElements(By.id("description")).size());
        assertTrue(driver.findElements(By.name("optionId")).size() > 1);
    }
}

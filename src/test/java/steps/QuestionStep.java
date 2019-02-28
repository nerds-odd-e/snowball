package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class QuestionStep {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private int totalCounter;
    private int scrumCounter;


    @Given("^Add a question \"([^\"]*)\" with dummy options$")
    public void add_a_question(String description) {
        add_a_question(description, "");
    }

    @Given("^Add a question \"([^\"]*)\" with dummy options and advice \"([^\"]*)\"$")
    public void add_a_question(String description, String advice) {
        new QuestionBuilder()
                .aQuestion(description, advice)
                .withWrongOption("Food")
                .withWrongOption("Drink")
                .withWrongOption("Country")
                .withWrongOption("Animal")
                .withCorrectOption("None of the above")
                .please();
    }

    @Given("^Add a question \"([^\"]*)\" of multiple answers$")
    public void add_a_question_of_multiple_answers(String description) {
        new QuestionBuilder()
                .aQuestion(description, "advice")
                .withCorrectOption("option1")
                .withWrongOption("option2")
                .withWrongOption("option3")
                .withCorrectOption("option4")
                .please();
    }

    @Given("^Add a question \"([^\"]*)\" of multiple answers with (\\d+) questions$")
    public void add_n_question_of_multiple_answers(String description, int n) {
        for (int i = 0; i < n; i++) {
            new QuestionBuilder()
                    .aQuestion(description, "advice")
                    .withCorrectOption("option1")
                    .withWrongOption("option2")
                    .withWrongOption("option3")
                    .withCorrectOption("option4")
                    .please();
        }
        site.visit(String.format("onlinetest/launchQuestion?question_count=%d", n));
    }

    @Given("^User is taking a onlineTest with (\\d+) questions$")
    public void user_is_taking_a_onlineTest_with_n_questions(int n) {
        for (int i = 0; i < n; i++)
            new QuestionBuilder()
                    .aQuestion()
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();
        site.visit(String.format("onlinetest/launchQuestion?question_count=%d", n));
    }

    @Given("^User is taking a onlineTest with (\\d+) multiple choice questions$")
    public void user_is_taking_a_onlineTest_with_multiple_choice_questions(int n) {
        for (int i = 0; i < n; i++)
            new QuestionBuilder()
                    .aQuestion(1)
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();
        site.visit(String.format("onlinetest/launchQuestion?question_count=%d", n));
    }

    @Given("^User is on the first question$")
    public void user_is_in_the_test_page() {
        site.visit("onlinetest/launchQuestion");
    }

    @Given("^User is on the second question$")
    public void user_is_on_the_second_question() {
        site.visit("onlinetest/launchQuestion");
        driver.clickRadioButton("None of the above");
        driver.clickButton("answer");
        driver.pageShouldContain("Question");
    }

    @Given("^User picked the wrong answer on the second question$")
    public void user_picked_the_wrong_answer_on_the_second_question() {
        site.visit("onlinetest/launchQuestion");
        driver.clickRadioButton("None of the above");
        driver.clickButton("answer");
        driver.pageShouldContain("Question");
        driver.clickRadioButton("Food");
        driver.clickButton("answer");
        driver.pageShouldContain("Advice");
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
            driver.clickRadioButton("correctOption");
            driver.clickButton("answer");
        }
    }

    @When("^User clicks \"([^\"]*)\" button$")
    public void user_clicks_button(String arg1) {
        site.visit("question");
    }

    @When("^User chooses the \"([^\"]*)\" answer$")
    public void userChoosesTheAnswer(String answer) {
        driver.clickRadioButton(answer);
    }

    @When("^User chooses \"([^\"]*)\" and \"([^\"]*)\" answers$")
    public void user_chooses_and_answers(String checkedOption1, String checkedOption2) {
        driver.clickCheckBox(checkedOption1);
        driver.clickCheckBox(checkedOption2);
    }

    @When("^User clicks the answer button$")
    public void user_clicks_the_answer_button() {
        driver.clickButton("answer");
    }

    @When("^User chooses the correct option$")
    public void user_chooses_the_correct_option() {
        driver.clickById("option5");
    }

    @When("^option(\\d+) is selected as correct answer$")
    public void optionIsSelectedAsCorrectAnswer(String optionId) {
        driver.clickButton("option" + optionId);
    }

    @When("^User clicks the next button$")
    public void user_clicks_the_next_button() {
        driver.clickButton("next");
    }

    @Then("^User should see a question and options$")
    public void user_should_see_a_question_and_options(DataTable question) {
        Map<String, String> questionMap = question.asMap(String.class, String.class);
        driver.expectElementWithIdToContainText("description", questionMap.get("description"));
        driver.expectRadioButtonWithText(questionMap.get("option1"));
        driver.expectRadioButtonWithText(questionMap.get("option2"));
        driver.expectRadioButtonWithText(questionMap.get("option3"));
        driver.expectRadioButtonWithText(questionMap.get("option4"));
        driver.expectRadioButtonWithText(questionMap.get("option5"));
    }

    @Then("^User sees the question progress as \"([^\"]*)\"$")
    public void user_sees_the_question_progress_as(String progress) {
        driver.expectElementWithIdToContainText("progress", progress);
    }

    @Then("^It should move to \"([^\"]*)\" page$")
    public void move_to_page(String redirected_page) {
        driver.pageShouldContain(redirected_page);
    }

    @Then("^User should see the \"([^\"]*)\" page$")
    public void user_should_see_the_page(String pageName) {
        driver.expectElementWithIdToContainText("title", "End Of Test");
    }

    @Then("^User should see \"([^\"]*)\" option \"([^\"]*)\" and text \"([^\"]*)\"$")
    public void user_should_see_options_and_text(String clazz, String color, String optionText) {
        String[] optionTexts = optionText.split(",\\s*");
        String cssSelector = "." + clazz.replace(" ", ".");
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        String[] actualTexts = elements.stream().map(WebElement::getText).toArray(String[]::new);
        assertThat(actualTexts, is(optionTexts));
        elements.forEach((e)->{
            assertEquals(e.getCssValue("color"), Color.fromString(color).asRgba());
        });
    }

    @Then("^User should see \"([^\"]*)\"$")
    public void user_should_see(String text) {
        driver.expectElementWithIdToContainValue("advice", text);
    }

    @Then("^Move to next question page$")
    public void move_to_next_question_page() {
        driver.pageShouldContain("Question");
    }

    @Then("^\"([^\"]*)\" should be shown$")
    public void is_shown(String currentPage) {
        driver.pageShouldContain(currentPage);
    }

    @When("^(\\d+)つ回答を選択する$")
    public void つ回答を選択する(int count) {
        driver.findElements(By.cssSelector("input[type=checkbox]")).stream().limit(count).forEach(option -> option.click());
    }

    @Then("^(\\d+)つ\"([^\"]*)\"の回答が選択されている事$")
    public void _つ_の回答が選択されている事(int count, String elementType) {
        assertTrue(driver.findElements(By.cssSelector("input[type="+elementType+"]")).size() > 0);
        int elementCount = driver.findElements(By.cssSelector("input[type="+elementType+"]:checked")).size();
        assertEquals(count, elementCount);
    }

    @When("^ユーザーがブラウザの戻るを実行する$")
    public void ユーザーがブラウザの戻るを実行する() {
        this.driver.getNavigate().back();
    }

    @Then("^質問(\\d+)の画面に遷移する$")
    public void 質問の画面に遷移する(int qustionNumber) {
        this.driver.expectElementWithIdToContainValue("currentQuestionIndex", String.valueOf(qustionNumber));
    }

    @Then("^アドバイスページにいる$")
    public void アドバイスページにいる() {
        assertEquals("Advice", driver.getCurrentTitle());
    }

    @And("^text \"([^\"]*)\" is color red$")
    public void textIsColorRed(String text) {
        List<WebElement> elements = driver.findElements(By.cssSelector(".alertMsg"));
        assertEquals(1, elements.size());
        assertEquals(elements.get(0).getText(), text);
        assertEquals(elements.get(0).getCssValue("color"), Color.fromString("#dc3545").asRgba());
    }

    @Given("^問題が出題される$")
    public void 問題が出題される() {
        site.visit("onlinetest/launchQuestion");
    }

    @Given("\"([^\"]*)\"に(\\d+)問が登録されている$")
    public void categoryNameに_問が登録されている(String categoryName, int numOfQuestions) {
        for (int i = 0; i < numOfQuestions; i++) {
            new QuestionBuilder()
                    .aQuestion(categoryName, null, categoryName)
                    .withCorrectOption("CorrectOption")
                    .please();
        }
    }

    @Then("^scrumが>=(\\d+)問が表示されること$")
    public void scrumが_問以上が表示されること(int count) {
        assertThat(scrumCounter,greaterThan(count));
    }

    @Then("^合計で(\\d+)問が表示されること$")
    public void 合計で_問が表示されること(int count) {
        assertEquals(count, totalCounter);
    }

    @Then("^scrumが(\\d+)問が表示されること$")
    public void scrumが_問が表示されること(int count) {
        assertEquals(count, scrumCounter);
    }

    @When("^startをクリックしてすべての問題を回答したとき$")
    public void startをクリックしてすべての問題を回答したとき() {
        totalCounter = 0;
        scrumCounter = 0;
        site.visit("onlinetest/launchQuestion");
        while (driver.getCurrentTitle().equals("Question")){
            totalCounter++;
            String categoryName = driver.findElementById("description").getText();
            if (categoryName.equals("scrum")) {
                scrumCounter++;
            }
            driver.clickRadioButton("CorrectOption");
            driver.clickButton("answer");
        }
    }
}

package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
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

import static org.junit.Assert.assertEquals;

public class QuestionStep {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^Add a question \"([^\"]*)\" \"([^\"]*)\"$")
    public void add_a_question(String description, String advice) throws Throwable {
        new QuestionBuilder()
                .aQuestion(description, advice)
                .withWrongOption("食べ物")
                .withWrongOption("飲み物")
                .withWrongOption("国")
                .withWrongOption("動物")
                .withCorrectOption("以上の何でもない")
                .please();
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

    @Given("^User is on the first question$")
    public void user_is_in_the_test_page() {
        site.visit("onlinetest/launchQuestion");
    }

    @Given("^User is on the second question$")
    public void user_is_on_the_second_question() throws Throwable {
        site.visit("onlinetest/launchQuestion");
        driver.clickRadioButton("None of the above");
        driver.clickButton("answer");
        driver.pageShouldContain("Question");
    }

    @Given("^User picked the wrong answer on the second question$")
    public void user_picked_the_wrong_answer_on_the_second_question() throws Throwable {
        site.visit("onlinetest/launchQuestion");
        driver.clickRadioButton("None of the above");
        driver.clickButton("answer");
        driver.pageShouldContain("Question");
        driver.clickRadioButton("Scrum is Rugby");
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
    public void user_sees_the_question_progress_as(String progress) throws Throwable {
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
    public void user_should_see_option_highlighted_and_text(String clazz, String color, String text) {
        String cssSelector = "." + clazz.replace(" ", ".");
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        assertEquals(1, elements.size());
        assertEquals(elements.get(0).getText(), text);
        assertEquals(elements.get(0).getCssValue("color"), Color.fromString(color).asRgba());
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
    public void つ回答を選択する(int count) throws Throwable {
        driver.findElements(By.cssSelector("input[type=checkbox]")).stream().limit(count).forEach(option -> option.click());
    }

    @Then("^(\\d+)つ回答が選択されている事$")
    public void つ回答が選択されている事(int count) throws Throwable {
        int elementCount = driver.findElements(By.cssSelector("input[type=checkbox]:checked")).size();
        assertEquals(count, elementCount);
    }

    @Then("^(\\d+)つラジオボタンの回答が選択されている事$")
    public void つラジオボタンの回答が選択されている事(int count) throws Throwable {
        int elementCount = driver.findElements(By.cssSelector("input[type=radio]:checked")).size();
        assertEquals(count, elementCount);
    }

    @When("^ユーザーがブラウザの戻るを実行する$")
    public void ユーザーがブラウザの戻るを実行する() throws Throwable {
        this.driver.getNavigate().back();
    }

    @Then("^質問(\\d+)の画面に遷移する$")
    public void 質問の画面に遷移する(int qustionNumber) throws Throwable {
        this.driver.expectElementWithIdToContainValue("currentQuestionIndex", String.valueOf(qustionNumber));
    }

    @Then("^アドバイスページにいる$")
    public void アドバイスページにいる() throws Throwable {
        assertEquals("Advice", driver.getCurrentTitle());
    }

    @And("^text \"([^\"]*)\" is color red$")
    public void textIsColorRed(String text) throws Throwable {
        List<WebElement> elements = driver.findElements(By.cssSelector(".alertMsg"));
        assertEquals(1, elements.size());
        assertEquals(elements.get(0).getText(), text);
        assertEquals(elements.get(0).getCssValue("color"), Color.fromString("#dc3545").asRgba());
    }

    @Given("^ユーザが(\\d+)個を選択する$")
    public void ユーザが_個を選択する(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^\"([^\"]*)\"をクリックする$")
    public void をクリックする(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^(\\d+)個の選択のうち、(\\d+)個が正しい$")
    public void 個の選択のうち_個が正しい(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^問題が存在する yes$")
    public void 問題が存在する_yes() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Question ページに遷移する$")
    public void question_ページに遷移する() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^問題が存在する No$")
    public void 問題が存在する_No() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^End Of Test ページに遷移する$")
    public void end_Of_Test_ページに遷移する() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Advice ページに遷移する$")
    public void advice_ページに遷移する() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^何も選択されていない$")
    public void 何も選択されていない() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}

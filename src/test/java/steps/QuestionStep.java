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

    @Given("^Add a question \"([^\"]*)\"$")
    public void add_a_question(String description) throws Throwable {
        new QuestionBuilder()
                .aQuestion(description, "Scrum is a framework for agile development.")
                .withWrongOption("Scrum is Rugby")
                .withWrongOption("Scrum is Baseball")
                .withWrongOption("Scrum is Soccer")
                .withWrongOption("Scrum is Sumo")
                .withCorrectOption("None of the above")
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

    @Given("^既に「スクラムにある役割は何がありますか？」という複数選択回答の問題がある$")
    public void 既に_スクラムにある役割は何がありますか_という複数選択回答の問題がある() {
        new QuestionBuilder()
                .aQuestion("スクラムにある役割は何がありますか？", "スクラムにはPO,SM,チームがあります。")
                .withWrongOption("BOSS")
                .withWrongOption("CTO")
                .withCorrectOption("PO")
                .withCorrectOption("SM")
                .withCorrectOption("Team")
                .please();
    }

    @When("^(\\d+)つ回答を選択する$")
    public void つ回答を選択する(int count) throws Throwable {
        driver.findElements(By.cssSelector("input[type=checkbox]")).stream().limit(count+1).forEach(option -> option.click());
    }

    @Then("^(\\d+)つ回答が選択されている事$")
    public void つ回答が選択されている事(int count) throws Throwable {
        int elementCount = driver.findElements(By.cssSelector("input[type=checkbox]:checked")).size();
        assertEquals(count, elementCount);
    }

    @And("^ユーザーの最初の問題です$")
    public void ユーザーの最初の問題です() {
        site.visit("onlinetest/launchQuestion");
    }

    @Then("^複数選択回答が表示されること$")
    public void 複数選択回答が表示されること() throws Throwable {
        int elementCount = driver.findElements(By.cssSelector("input[type=checkbox]")).size();
        assertEquals(elementCount, 5);
    }


    @Then("^問題を表示する$")
    public void 問題を表示する() throws Throwable {
        driver.expectElementWithIdToContainValue("answer", "question");
    }

    @Then("^選択された問題を表示する$")
    public void 選択された問題を表示する() throws Throwable {
        driver.expectElementWithIdToContainText("description", "question");
    }

    @Then("^選択肢のチェックボックスを表示する$")
    public void 選択肢のチェックボックスを表示する() throws Throwable {
        driver.expectCheckBoxWithText("PO");
    }

    @Then("^Answerボタンを表示する$")
    public void answerボタンを表示する() throws Throwable {
        driver.expectElementWithIdToContainText("answer", "answer");
    }

    @Given("^アドミンが質問を２個用意する$")
    public void アドミンが質問を２個用意する() throws Throwable {
        new QuestionBuilder()
                .aQuestion("元気ですか？", "残念です")
                .withWrongOption("はい")
                .withWrongOption("いいえ")
                .withCorrectOption("はい")
                .please();
        new QuestionBuilder()
                .aQuestion("楽しいですか？", "残念です")
                .withWrongOption("はい")
                .withWrongOption("いいえ")
                .withCorrectOption("はい")
                .please();
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

    @When("^\"([^\"]*)\"を選択した$")
    public void を選択した(String text) throws Throwable {
        driver.clickCheckBox(text);
    }

    @Then("^EndOfTheTestが表示される事$")
    public void endofthetestが表示される事() throws Throwable {
        assertEquals("End Of Test", driver.getCurrentTitle());
    }
}

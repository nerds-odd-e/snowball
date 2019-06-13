package steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.types.ObjectId;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.odde.snowball.model.base.Repository.repo;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class QuestionStep {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private int totalCounter;
    private int scrumCounter;
    private int numberOfCorrectAnsweredQuestion;
    private int currentTestTotalQuestions;
    private final String add_question_url = site.baseUrl() + "onlinetest/add_question.jsp";
    private final CategoryBuilder categoryBuilder = new CategoryBuilder();

    private final String login_url = site.baseUrl() + "login.jsp";

    private void visitLoginPage() {
        driver.visit(login_url);
        driver.expectPageToContainText("Login Massive Mailer");
    }


    @Given("^Add a question \"([^\"]*)\" with dummy options$")
    public void add_a_question(String description) {
        add_a_question(description, "");
    }

    @Given("^Add a question \"([^\"]*)\" with dummy options and advice \"([^\"]*)\"$")
    public void add_a_question(String description, String advice) {
        new QuestionBuilder()
                .aQuestion(description, advice, categoryBuilder.categoryByName("Scrum"))
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
                .aQuestion(description, "advice", categoryBuilder.categoryByName("Scrum"))
                .mutipleSelections()
                .withCorrectOption("correctOption1")
                .withWrongOption("wrongOption1")
                .withWrongOption("wrongOption2")
                .withCorrectOption("correctOption2")
                .please();
    }

    @Given("^User is taking a onlineTest with (\\d+) questions$")
    public void user_is_taking_a_onlineTest_with_n_single_questions(int totalQuestions) {
        this.currentTestTotalQuestions = totalQuestions;
        for (int i = 0; i < totalQuestions; i++)
            new QuestionBuilder()
                    .aQuestion(repo(Category.class).findFirstBy("name", "Scrum"))
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();
        site.visit(String.format("onlinetest/launchQuestion?question_count=%d", totalQuestions));
    }

    @Given("^User is taking a onlineTest with (\\d+) single choice questions$")
    public void user_is_taking_a_onlineTest_with_n_questions(int totalQuestions) {
        user_is_taking_a_onlineTest_with_n_single_questions(totalQuestions);
    }

    @Given("^User is taking a onlineTest with (\\d+) multiple choice questions$")
    public void user_is_taking_a_onlineTest_with_multiple_choice_questions(int n) {
        for (int i = 0; i < n; i++) {
            add_a_question_of_multiple_answers("multiple choice question");
        }
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
        driver.click("#answer");
        driver.expectPageToContainText("Question");
    }

    @Given("^User picked the wrong answer on the second question$")
    public void user_picked_the_wrong_answer_on_the_second_question() {
        site.visit("onlinetest/launchQuestion");
        driver.clickRadioButton("None of the above");
        driver.click("#answer");
        driver.expectPageToContainText("Question");
        driver.clickRadioButton("Food");
        driver.click("#answer");
        driver.expectPageToContainText("Advice");
    }

    @Given("^User arrives at advice page$")
    public void user_arrives_at_advice_page() {
        site.visit("question");
        driver.click("#wrongOption");
        driver.click("#answer");
    }

    @Given("^User answered \"([^\"]*)\" times in the test page$")
    public void user_answered_times_in_the_test_page(String answeredCount) {
        driver.expectElementToContainText("#answeredCount", answeredCount);
        driver.expectPageToContainText("Question");
    }

    @Given("^User answered (\\d+) questions correctly$")
    public void user_answered_correctly_the(int answeredCount) {
        this.numberOfCorrectAnsweredQuestion = answeredCount;
        for (int i = 0; i < answeredCount; ++i) {
            driver.clickRadioButton("correctOption");
            driver.click("#answer");
        }
    }

    @And("^User answered all other questions wrong$")
    public void userAnsweredAllOtherQuestionsWrong() {
        for (int i = 0; i < currentTestTotalQuestions - numberOfCorrectAnsweredQuestion; ++i) {
            driver.clickRadioButton("wrongOption");
            driver.click("#answer");
            driver.click("#next");
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
        driver.click("#answer");
    }

    @When("^User chooses the correct option$")
    public void user_chooses_the_correct_option() {
        driver.click("#option5");
    }

    @When("^option(\\d+) is selected as correct answer$")
    public void optionIsSelectedAsCorrectAnswer(String optionId) {
        driver.click("#" + ("option" + optionId));
    }

    @When("^User clicks the next button$")
    public void user_clicks_the_next_button() {
        driver.click("#next");
    }

    @Then("^User should see a question and options$")
    public void user_should_see_a_question_and_options(DataTable question) {
        Map<String, String> questionMap = question.asMap(String.class, String.class);
        driver.expectElementToContainText("#description", questionMap.get("description"));
        driver.expectRadioButtonWithText(questionMap.get("option1"));
        driver.expectRadioButtonWithText(questionMap.get("option2"));
        driver.expectRadioButtonWithText(questionMap.get("option3"));
        driver.expectRadioButtonWithText(questionMap.get("option4"));
        driver.expectRadioButtonWithText(questionMap.get("option5"));
    }

    @Then("^User sees the question progress as \"([^\"]*)\"$")
    public void user_sees_the_question_progress_as(String progress) {
        driver.expectElementToContainText("#progress", progress);
    }

    @Then("^It should move to \"([^\"]*)\" page$")
    public void move_to_page(String redirected_page) {
        driver.expectPageToContainText(redirected_page);
    }

    @Then("^User should see the \"([^\"]*)\" page$")
    public void user_should_see_the_page(String pageName) {
        driver.expectElementToContainText("#title", "End Of Test");
    }

    @Then("^User should see \"([^\"]*)\" option \"([^\"]*)\" and text \"([^\"]*)\"$")
    public void user_should_see_options_and_text(String clazz, String color, String optionText) {
        List<String> optionTexts = asList(optionText.split(",\\s*"));
        String cssSelector = "." + clazz.replace(" ", ".");
        driver.expectElementsToHaveTexts(cssSelector, optionTexts);
        driver.forEachElement(
                cssSelector,
                (e) -> assertEquals(e.getCssValue("color"), Color.fromString(color).asRgba()));
    }

    @Then("^User should see \"([^\"]*)\"$")
    public void user_should_see(String text) {
        driver.expectElementToContainText("#advice", text);
    }

    @Then("^Move to next question page$")
    public void move_to_next_question_page() {
        driver.expectPageToContainText("Question");
    }

    @Then("^\"([^\"]*)\" should be shown$")
    public void is_shown(String currentPage) {
        driver.expectPageToContainText(currentPage);
    }

    @Then("^(\\d+)つ\"([^\"]*)\"の回答が選択されている事$")
    public void _つ_の回答が選択されている事(int count, String elementType) {
        driver.expectElementToExist("input[type=" + elementType + "]");
        driver.expectElementToExist("input[type=" + elementType + "]:checked", count);
    }

    @When("^ユーザーがブラウザの戻るを実行する$")
    public void ユーザーがブラウザの戻るを実行する() {
        this.driver.getNavigate().back();
    }

    @Then("^質問(\\d+)の画面に遷移する$")
    public void 質問の画面に遷移する(int qustionNumber) {
        this.driver.expectElementToContainText("#currentQuestionIndex", String.valueOf(qustionNumber));
    }

    @Then("^アドバイスページにいる$")
    public void アドバイスページにいる() {
        driver.expectTitleToBe("Advice");
    }

    @And("^text \"([^\"]*)\" is color red$")
    public void textIsColorRed(String text) {
        driver.expectElementToContainText(".alertMsg", text);
        driver.forEachElement(".alertMsg", e ->
                assertEquals(e.getCssValue("color"), Color.fromString("#dc3545").asRgba())
        );
    }

    @Given("^問題が出題される$")
    public void 問題が出題される() {
        site.visit("onlinetest/launchQuestion");
    }

    @Given("In \"([^\"]*)\" there are (\\d+) questions$")
    public void categoryNameに_問が登録されている(String categoryName, int numOfQuestions) {
        ObjectId categoryId = categoryBuilder.categoryByName(categoryName);

        for (int i = 0; i < numOfQuestions; i++) {
            new QuestionBuilder()
                    .aQuestion(categoryName, null, categoryId)
                    .withCorrectOption("CorrectOption")
                    .please();
        }
    }

    @Then("^there should be >=(\\d+) Scrum questions$")
    public void scrumが_問以上が表示されること(int count) {
        assertThat(scrumCounter, greaterThanOrEqualTo(count));
    }

    @Then("^there should be (\\d+) Scrum questions$")
    public void scrumが_問が表示されること(int count) {
        assertEquals(count, scrumCounter);
    }

    @Then("^in total (\\d+) questions$")
    public void 合計で_問が表示されること(int count) {
        assertEquals(count, totalCounter);
    }

    @When("^do a test with (\\d+) questions$")
    public void startをクリックしてすべての問題を回答したとき(int count) {
        totalCounter = 0;
        scrumCounter = 0;
        OnlineTest onlineTest = new OnlineTest(count);
        while (onlineTest.hasNextQuestion()) {
            totalCounter++;
            Question currentQuestion = onlineTest.getCurrentQuestion();
            if (currentQuestion.categoryName().equals("Scrum")) {
                scrumCounter++;
            }
            onlineTest.addAnsweredQuestionNumber();
        }
    }

    @Given("^\"([^\"]*)\"ユーザが登録されている$")
    public void ユーザが登録されている(String arg1) {
        User user = new User("terry@hogehoge.com");
        user.setupPassword("11111111");
        user.save();
    }

    @Given("^\"([^\"]*)\"がログインしている$")
    public void がログインしている(String arg1) {
        visitLoginPage();
        driver.setTextField("email", "terry@hogehoge.com");
        driver.setTextField("password", "11111111");
        driver.click("#login");
    }

    @When("^質問の内容を入力してAddボタンを押す")
    public void 質問の内容を入力してAddボタンを押す() {
        driver.visit(add_question_url);
        driver.selectDropdownByValue("type", "single");
        driver.setTextField("description", "PrivateDescription");
        driver.setTextField("option1", "option1");
        driver.setTextField("option2", "option2");
        driver.click("#option2");
        driver.click("#add_button");
    }

    @Then("^\"([^\"]*)\"の特訓トップページに追加した質問が表示される$")
    public void の特訓トップページに追加した質問が表示される(String arg1) {
        driver.expectPageToContainText("PrivateDescription");
    }

}

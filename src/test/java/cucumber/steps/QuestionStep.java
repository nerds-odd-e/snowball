package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.OnlineQuiz;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;
import org.bson.types.ObjectId;
import org.openqa.selenium.support.Color;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.*;

public class QuestionStep {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private int totalCounter;
    private int scrumCounter;
    private int numberOfCorrectAnsweredQuestion;
    private int currentTestTotalQuestions;
    private final String add_question_url = site.baseUrl() + "onlinetest/add_question.jsp";
    private final String dashboard_url = site.baseUrl() + "dashboard";
    private final String onlinetest_url = site.baseUrl() + "onlinetest/launchQuiz";
    private final String practice_url = site.baseUrl() + "launchPractice";

    private final String login_url = site.baseUrl() + "login.jsp";

    private void visitLoginPage() {
        driver.visit(login_url);
        driver.expectPageToContainText("Login Massive Mailer");
    }

    @Given("^There are questions with dummy options:$")
    public void add_questions(List<Map<String, String>> questions) {
        questions.forEach(questionAttributes -> new QuestionBuilder()
                .aQuestion(
                        questionAttributes.getOrDefault("description", "What is scrum"),
                        questionAttributes.getOrDefault("advice", ""),
                        questionAttributes.getOrDefault("category", "Scrum"))
                .withWrongOption("Food")
                .withWrongOption("Drink")
                .withWrongOption("Country")
                .withWrongOption("Animal")
                .withCorrectOption(
                        questionAttributes.getOrDefault("correctOption", "None of the above"))
                .please()
        );
    }

    @Given("^there is a question \"([^\"]*)\" of multiple answers$")
    public void add_a_question_of_multiple_answers(String description) {
        new QuestionBuilder()
                .aQuestion(description, "advice", "Scrum")
                .mutipleSelections()
                .withCorrectOption("correctOption1")
                .withWrongOption("wrongOption1")
                .withWrongOption("wrongOption2")
                .withCorrectOption("correctOption2")
                .please();
    }

    @Given("^there are (\\d+) questions of category \"([^\"]*)\"$")
    public void create_questions_with_category(int numberOfQuestions, String category) {
        for (int i = 0; i < numberOfQuestions; i++) {
            QuestionBuilder.buildDefaultQuestion(category).please();
        }
    }

    @Given("(\\d+)日前に回答した問題が(\\d+)個存在する")
    public void create_answered_questions(int pastDays, int numberOfQuestions) {
        for (int i = 0; i < numberOfQuestions; i++) {
            QuestionBuilder.buildAnsweredQuestion(pastDays).please();
        }
    }

    @Given("問題が(\\d+)個存在する")
    public void create_questions(int numberOfQuestions) {
        create_questions_with_category(numberOfQuestions, "Category");
    }

    @When("^OnlineTestを開始する$")
    public void start_onlinetest() {
        site.visit("onlinetest/launchQuiz");
    }

    @Given("^User is taking a onlineTest with (\\d+) questions$")
    public void user_is_taking_a_onlineTest_with_n_single_questions(int totalQuestions) {
        this.currentTestTotalQuestions = totalQuestions;
        site.visit(String.format("onlinetest/launchQuiz?question_count=%d", totalQuestions));
    }

    @Given("^User is taking a onlineTest with (\\d+) questions and there are enough questions$")
    public void user_is_taking_a_onlineTest_with_all_questions(int totalQuestions) {
        create_questions_with_category(totalQuestions, "aCategory");
        user_is_taking_a_onlineTest_with_n_single_questions(totalQuestions);
    }

    @Given("^User is taking a onlineTest with (\\d+) single choice questions and there are enough questions$")
    public void user_is_taking_a_onlineTest_with_n_questions(int totalQuestions) {
        user_is_taking_a_onlineTest_with_all_questions(totalQuestions);
    }

    @Given("^User is taking a onlineTest with (\\d+) multiple choice questions and there are enough questions$")
    public void user_is_taking_a_onlineTest_with_multiple_choice_questions(int n) {
        for (int i = 0; i < n; i++) {
            add_a_question_of_multiple_answers("multiple choice question");
        }
        user_is_taking_a_onlineTest_with_n_single_questions(n);
    }

    @Given("^User is on the first question$")
    public void user_is_in_the_test_page() {
        site.visit("onlinetest/launchQuiz");
    }

    @Given("^User is on the second question$")
    public void user_is_on_the_second_question() {
        site.visit("onlinetest/launchQuiz");
        driver.clickRadioButton("None of the above");
        driver.click("#answer");
        driver.expectPageToContainText("Question");
    }

    @Given("^User picked the wrong answer on the second question$")
    public void user_picked_the_wrong_answer_on_the_second_question() {
        site.visit("onlinetest/launchQuiz");
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

    @Given("^User answered (\\d+) questions? correctly$")
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
    public void user_should_see_a_question_and_options(Map<String, String> questionMap) {
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
        driver.expectPageToContainText(text);
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
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(count);
        while (onlineTest.getCurrentQuestion() != null) {
            totalCounter++;
            Question currentQuestion = onlineTest.getCurrentQuestion();
            onlineTest.answerCurrentQuestion(singletonList(new ObjectId().toString()), null, LocalDate.now());
            if (currentQuestion.categoryName().equals("Scrum")) {
                scrumCounter++;
            }
        }
    }

    @Given("^\"([^\"]*)\"ユーザが登録されている$")
    public void ユーザが登録されている(String userName) {
        User user = new User(userName.toLowerCase() + "@hogehoge.com");
        user.setupPassword("11111111");
        user.save();
    }

    @Given("^\"([^\"]*)\"がログインしている$")
    public void がログインしている(String userName) {
        visitLoginPage();
        driver.setTextField("email", userName.toLowerCase() + "@hogehoge.com");
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

    @When("^\"([^\"]*)\"でログインする$")
    public void でログインする(String userName) {
        visitLoginPage();
        driver.setTextField("email", userName.toLowerCase() + "@hogehoge.com");
        driver.setTextField("password", "11111111");
        driver.click("#login");
    }

    @Then("ダッシュボードに問題が{string}")
    public void ダッシュボードに問題が(String isDisplay) {
        //ダッシュボードページを開く
        driver.visit(dashboard_url);

        //description文字列がページにisDisplay
        boolean isExists = !driver.findElementsWithoutWait(".description").isEmpty();
        assertEquals(isExists, isDisplay.equals("表示される"));
    }

    @Then("オンラインテストに問題が{string}")
    public void オンラインテストに問題が(String isDisplay) {
        //ダッシュボードページを開く
        driver.visit(onlinetest_url);

        //description文字列がページにisDisplay
        String text = driver.findElementsWithoutWait("#description").get(0).getText();
        if (isDisplay.equals("表示される")) {
            assertEquals("What is scrum?", text);
        } else {
            assertEquals("", text);
        }
    }

    @Then("プラクティスに問題が{string}")
    public void プラクティスに問題が(String isDisplay) {
        //ダッシュボードページを開く
        driver.visit(practice_url);

        //description文字列がページにisDisplay
        if (isDisplay.equals("表示される")) {
            String text = driver.findElementsWithoutWait("#description").get(0).getText();
            assertEquals("What is scrum?", text);
        } else {
            driver.expectURLToContain("/practice/completed_practice.jsp");
        }
    }

    @When("ホームに遷移")
     public void ホームに遷移() {
        driver.visit(dashboard_url);
    }

    @Given("時間を{int}日経過させる")
    public void 時間を経過させる(int dayNumber) {
        // TODO
        // 回答履歴の日付を{dayNumber}日分過去の日付にする
    }

}

package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.Question;
import com.odde.snowball.model.onlinetest.Record;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.time.LocalDate;
import java.util.Date;

import static com.odde.snowball.model.base.Repository.repo;

public class PracticeTestsStepDefs {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @And("I start a practice with 1 question")
    public void iStartAPracticeWithQuestion() {
        driver.clickButtonByName("start_practice_button");
    }

    @When("I answer {int} question correctly")
    public void iAnswerQuestionCorrectly(int count) {
        for (int i = 0; i < count; i++) {
            driver.clickRadioButton("None of the above");
            driver.click("#answer");
        }
    }

    @Then("I should see {string}")
    public void iShouldSee(String expectedString) {
        driver.expectPageToContainText(expectedString);
    }

    @When("I start a fixed repetition practice with {int} question again on the same day")
    public void iStartAFixedRepetitionPracticeWithQuestionAgainOnTheSameDay(int arg0) {
        //TODO change db date of previous entry to day before
    }

    @Given("User is taking a practiceTest")
    public void userIsTakingAPracticeTest() {
        site.visit("launchPractice?question_count=2");
    }

    @When("User answered {int} question wrongly")
    public void userAnsweredQuestionWrongly(int questionNum) {
        for (int i = 0; i < questionNum; i++) {
            driver.clickRadioButton("Animal");
            driver.click("#answer");
        }
    }

    //　TODO ここから
    @Then("User should see Advice page")
    public void userShouldSeeAdvicePage() {
        driver.expectPageToContainText("Advice");
    }

    @When("User clicks on Next on Advice page")
    public void userClicksOnNextOnAdvicePage() {
        driver.click("#next");
    }

    @When("プラクティスを開始")
    public void プラクティスを開始() {
        // Write code here that turns the phrase above into concrete actions
        driver.clickButtonByName("start_practice_button");
    }

    @When("問題に{int}回正解する")
    public void 問題に_回正解する(Integer count) {
        // Write code here that turns the phrase above into concrete actions
        for (int i = 0; i < count; i++) {
            driver.clickRadioButton("correctOption");
            driver.click("#answer");
        }
    }

    @Then("{string}のメッセージが表示される")
    public void のメッセージが表示される(String expectedString) {
//        driver.takeScreenshot("tmp/hoge");
        // Write code here that turns the phrase above into concrete actions
        driver.expectPageToContainText(expectedString);
    }

    @Given("問題1と問題2が存在する")
    public void 問題1と問題2が存在する() {
        // 問題1はbackgroundで登録済み
        new QuestionBuilder()
                .aQuestion("Q2", "advice", "Scrum")
                .withWrongOption("wrongOption1")
                .withCorrectOption("correctOption1")
                .please();
    }

    @Given("問題(\\d+)に解答する")
    public void 問題に解答する(Integer num) {

        Question question = repo(Question.class)
                .findFirstBy("description", "Q" + num);

        User user = User.getUserByEmail("gulliver@email.com");

        Record record = Record.getOrInitializeRecord(user, question);
        record.setLastUpdated(LocalDate.now());
        record.setCycleState(1);
        record.save();
    }

    @Then("問題(\\d+)が出題される")
    public void 問題_が出題される(Integer num) {
        driver.expectPageToContainText("Q" + num);
    }

    @When("問題{int}に正解する")
    public void 問題_に正解する(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    // TODO 空実装
    @Given("今日は{int}年{int}月{int}日である")
    public void 今日は_年_月_日である(Integer int1, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Given("問題{int}と問題{int}が存在する")
    public void 問題_と問題_が存在する(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    // TODO 解答日時未設定
    @Given("問題{int}の解答日時が{int}年{int}月{int}日である")
    public void 問題_の解答日時が_年_月_日である(Integer int1, Integer int2, Integer int3, Integer int4) {
        // Write code here that turns the phrase above into concrete actions
        site.visit("launchPractice?question_count=2");
        QuestionStep questionStep = new QuestionStep();
        questionStep.user_answered_correctly_the(int1);
    }

    @Given("問題{int}は解答していない")
    public void 問題_は解答していない(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
        site.visit("dashboard");
    }
}

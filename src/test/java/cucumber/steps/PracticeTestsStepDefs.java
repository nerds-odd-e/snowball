package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Question;
import com.odde.snowball.model.onlinetest.Record;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;

import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.assertEquals;

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
        site.visit("launchPractice");
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
        driver.takeScreenshot("tmp/update");
        // Write code here that turns the phrase above into concrete actions
        driver.expectPageToContainText(expectedString);
    }

    @Given("問題{int}と問題{int}が存在する")
    public void 問題1と問題2が存在する(Integer _first, Integer _second) {
        // 問題1はbackgroundで登録済み
        new QuestionBuilder()
                .aQuestion("Q2", "advice", "Scrum")
                .withWrongOption("wrongOption")
                .withCorrectOption("correctOption")
                .please();
    }

    @Given("{string}が問題{int}に解答する")
    public void が問題_に解答する(String user_name, Integer num) {
        Question question = repo(Question.class)
                .findFirstBy("description", "Q" + num);

        User user = User.getUserByEmail(user_name + "@email.com");

        Record record = Record.getOrInitializeRecord(user, question);
        record.setLastUpdated(LocalDate.now());
        record.setCycleState(1);
        record.save();
    }

    @Then("問題(\\d+)が出題される")
    public void が出題される(Integer description) {
        driver.takeScreenshot("tmp/hoge1");
        driver.expectElementToContainText("#description", "Q" + description);
        String foundStr= "";
        for (WebElement e : driver.findElements("#description")) {
            foundStr = e.getText();
        }
        assertEquals("Q" + description, foundStr);
    }

    @When("問題{int}に正解する")
    public void 問題_に正解する(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        driver.clickRadioButton("correctOption");
        driver.click("#answer");
    }

    // TODO 空実装
    @Given("今日は{int}年{int}月{int}日である")
    public void 今日は_年_月_日である(Integer int1, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    // TODO 解答日時未設定
    @Given("問題{int}の解答日時が{int}年{int}月{int}日である")
    public void 問題_の解答日時が_年_月_日である(int questionNum, Integer year, Integer month, Integer dayOfMonth) {
        Question question = repo(Question.class)
                .findFirstBy("description", "Q" + questionNum);

        User user = User.getUserByEmail("mary@email.com");

        Record record = Record.getOrInitializeRecord(user, question);
        record.setLastUpdated(LocalDate.of(year, month, dayOfMonth));
        record.setCycleState(record.getCycleState() + 1);
        record.save();
    }

    @Given("問題{int}は解答していない")
    public void 問題_は解答していない(Integer int1) {
    }

    @Given("{string}に問題1に対して{string}回目の解答をした")
    public void に問題1に対して_回目の解答をした(String lastAnsweredDate, String answeredCount) {
        Question question = repo(Question.class).findFirstBy("description", "Q1");
        User user = User.getUserByEmail("mary@email.com");

        for (int i = 0; i < Integer.valueOf(answeredCount); i++) {
            question.recordQuestionForUser(user, LocalDate.parse(lastAnsweredDate));
        }
    }

    @When("{string}にテストを開始")
    public void にテストを開始(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("問題{int}が{string}")
    public void 問題_が(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}

package cucumber.steps;

import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.AnswerInfo;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.Question;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;
import org.openqa.selenium.WebElement;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Then("問題(\\d+)が出題される")
    public void が出題される(Integer description) {
        driver.takeScreenshot("tmp/hoge1");
        driver.expectElementToContainText("#description", "Q" + description);
        String foundStr = "";
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

    @Given("問題{int}は解答していない")
    public void 問題_は解答していない(Integer int1) {
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

    @Given("問題が登録されている")
    public void 問題が登録されている() {
        // todo Questionを作成　目印になる情報を盛り込む
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("User is taking a practiceTest of {string}")
    public void userIsTakingAPracticeTestOf(String category) {
        driver.clickRadioButton(category);
        driver.clickButtonByName("start_practice_button");
    }

    @Given("Q2を前日に解答して間違えている")
    public void 誤答して１日たった問題と未回答の問題がある() {

        // ログインしたユーザに対して誤答したAnswerInfoをAdd

        // ユーザが先頭の人と仮定
        List<Question> questionList = repo(Question.class).findAll();
        List<User> userList = repo(User.class).findAll();
        User loginUser = userList.get(0);

        Calendar calYesterday = Calendar.getInstance();
        calYesterday.add(Calendar.DATE, -1);

        Calendar calToday  = Calendar.getInstance();
        calToday.add(Calendar.DATE, 0);

        // 新しい回答情報を作成
        AnswerInfo ansInfo = new AnswerInfo(questionList.get(1).stringId(), calYesterday.getTime(), 1, calToday.getTime());

        loginUser.addAnswerInfo(ansInfo);
        loginUser.save();

    }

    @Then("誤答して１日たった問題が優先して表示される")
    public void 誤答して１日たった問題が優先して表示される() {


    }

    private void createAnsweredQuestion(User user, int elapsedDays, int correctCount) {

        // 次回表示予定日を現在時刻とする
        Date nextShowDate = new Date();

        // 最終回答日を今日から経過日数前の日付とする
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextShowDate);
        calendar.add(Calendar.DAY_OF_MONTH, elapsedDays * -1);
        Date answeredDate = calendar.getTime();

        // 新規question作成
        Category category = repo(Category.class).findFirstBy("name", "Retro");
        Question question = new Question("test", "advice", category.getId(), false, true);
        question.save();

        // 作成したquestionに回答情報を追加
        AnswerInfo answerInfo = new AnswerInfo(question.stringId(), answeredDate, correctCount, nextShowDate);

        // 回答情報を登録
        user.addAnswerInfo(answerInfo);
        user.save();
    }
}

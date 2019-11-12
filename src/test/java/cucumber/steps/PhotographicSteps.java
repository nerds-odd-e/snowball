package cucumber.steps;


import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Question;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.List;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class PhotographicSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private int currentTestTotalQuestions;

    //Scenario 1
    @When("訓練を開始")
    public void 訓練を開始() {
        site.visit("onlinetest/launchQuiz");
    }

    @When("問題を1問解く")
    public void 問題を1問解く() {
        driver.clickRadioButton("correctOption");
        driver.click("#answer");
    }

    @When("訓練再開始")
    public void 訓練開始() {
        site.visit("onlinetest/launchQuiz");
    }

    @Then("表示文が {string}")
    public void 表示文が(String text) {
        driver.expectPageToContainText(text);
    }

    // Scenario 2
    @When("User click the Start Practice button")
    public void click_startPractice() {
        driver.click("#start_practice_button");
    }

    @And("User click HOME button")
    public void click_home() {
        driver.click("#home");
    }

    // Scenario 3
    @Given("質問{int}ある")
    public void 質問を作る(int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            //todo: デフォルト質問が本当にnumberOfTimes個を作られているかの確認が必要
            QuestionBuilder.buildDefaultQuestion("category").please();
        }
    }

    @Given("システムは、テストユーザ（\"test\"）を利用する。")
    public void テストユーザを作る() {
        User user = new User("test");
        user.setupPassword("test");
        user.save();
    }

    @Given("ユーザは、解答されている質問{int}個がある")
    public void ユーザは_解答されている質問_個がある(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
        List<Question> visibleQuestions = repo(Question.class).findAll().stream()
                .filter(q -> q.isVisibleForUser(User.getUserByEmail("test")))
                .collect(Collectors.toList());

    }

    @Given("解答されてない質問{int}個がある")
    public void 解答されてない質問_個がある(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("{int}問のテストを行う")
    public void 問のテストを行う(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("質問{int}個が表示される")
    public void 質問_個が表示される(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();


    }
}

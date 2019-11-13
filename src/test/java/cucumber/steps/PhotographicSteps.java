package cucumber.steps;


import com.odde.snowball.factory.QuestionBuilder;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class PhotographicSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private int currentTestTotalQuestions;

    //Scenario 1
    @Given("質問1ある")
    public void 質問1ある() {
        QuestionBuilder.buildDefaultQuestion("category").please();
    }

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

    @Given("^User is taking a onlinePractice with (\\d+) questions$")
    public void user_is_taking_a_onlinePractice_with_n_single_questions(int totalQuestions) {
        this.currentTestTotalQuestions = totalQuestions;
        site.visit(String.format("launchPractice?question_count=%d", totalQuestions));
    }

    @Given("^User is taking a onlinePractice with (\\d+) questions and there are enough questions$")
    public void user_is_taking_a_onlinePractice_with_all_questions(int totalQuestions) {

        user_is_taking_a_onlinePractice_with_n_single_questions(totalQuestions);
    }

    @And("User click HOME button")
    public void click_home() {
        driver.click("#home");
    }

    @Then("^I return to dashboard page$")
    public void inDashboard() {
        driver.expectURLToContain("/dashboard");
    }

    @Given("質問{int}ある")
    public void 質問_ある(Integer questionCount) {
        for (int i = 0; i < questionCount; i++) {
            QuestionBuilder.buildDefaultQuestion("category").please();
        }
    }

    // Scenario 3
    @Given("システムに問題が{int}問ある")
    public void システムに問題が_問ある(Integer int1) {
        for (int i = 0; i < int1; i++) {
            new QuestionBuilder()
                    .aQuestion("question" + i, "advice", "Scrum")
                    .withCorrectOption("correctOption1")
                    .withWrongOption("wrongOption1")
                    .withWrongOption("wrongOption2")
                    .please();
        }
    }

    @When("訓練を開始する")
    public void 訓練を開始する() {
        site.visit("launchPractice");
    }

    @When("{int}問のテストに正解する")
    public void 問のテストに正解する(Integer numberAnsweredCorrectly) {
        for (int i = 0; i < numberAnsweredCorrectly; i++) {
            driver.clickRadioButton("correctOption1");
            driver.click("#answer");
        }
    }

    @When("ホームに戻って中断する")
    public void ホームに戻って中断する() {
        site.visit("dashboard");
    }

    @Then("正解していない問題が{int}問ある")
    public void 正解していない問題が_問ある(Integer numberAnsweredIncorrectly) {
        driver.expectPageToContainText(String.format("1/%d",  numberAnsweredIncorrectly));
    }


}

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


}

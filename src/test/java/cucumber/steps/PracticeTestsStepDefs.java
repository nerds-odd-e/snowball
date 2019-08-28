package cucumber.steps;

import com.odde.snowball.model.onlinetest.Category;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

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
    public void のメッセージが表示される(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}

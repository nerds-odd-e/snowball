package cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;
import org.openqa.selenium.WebElement;

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

    @Given("User is taking a practiceTest of {string}")
    public void userIsTakingAPracticeTestOf(String category) {
        driver.clickRadioButton(category);
        driver.clickButtonByName("start_practice_button");
    }

}

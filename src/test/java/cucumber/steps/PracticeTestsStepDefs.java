package cucumber.steps;

import cucumber.api.java.en.And;
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
        //TODO Test after implementing questions and answer page
//        for (int i = 0; i < count; i++) {
//            driver.clickRadioButton("None of the above");
//            driver.click("#answer");
//        }
    }

    @Then("I should see {string}")
    public void iShouldSee(String expectedString) {
//        driver.expectPageToContainText(expectedString);
        //TODO Check after implementing the "Test Complete" page
    }

    @When("I start a fixed repetition practice with {int} question again on the same day")
    public void iStartAFixedRepetitionPracticeWithQuestionAgainOnTheSameDay(int arg0) {
        //TODO change db date of previous entry to day before
    }
}

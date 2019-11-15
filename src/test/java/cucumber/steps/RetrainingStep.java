package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class RetrainingStep {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("問題が(\\d+)個存在し、Practiceで出題される問題が(\\d+)個存在する")
    public void Practiceで出題される問題存在する(int allQuestionCount, int practiceQuestionCount) {

        for (int i = 0; i < allQuestionCount; i++) {
            QuestionBuilder.buildDefaultQuestion("Category").please();
        }

        site.visit("launchPractice");

        for (int i = 0; i < allQuestionCount - practiceQuestionCount; i++) {
            driver.clickRadioButton("correctOption");
            driver.click("#answer");
        }
    }

    @Then("retrainingボタンが^\"([^\"]*)\"$")
    public void retrainingボタンが(String isDisplay) {
        if (isDisplay.equals("表示される")) {
            driver.expectPageToContainText("Start Retraining");
        } else {
            driver.expectPageNotToContainText("Start Retraining");
        }
    }

    @When("retrainingを開始する")
    public void retrainingを開始する() {
        site.visit("launchRetraining");
    }
}

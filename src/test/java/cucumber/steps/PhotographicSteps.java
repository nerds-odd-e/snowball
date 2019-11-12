package cucumber.steps;


import com.odde.snowball.factory.QuestionBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class PhotographicSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("問題が一問だけある")
    public void 問題が一問だけある() {
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

    @When("訓練開始")
    public void 訓練開始() {
        site.visit("onlinetest/launchQuiz");
    }

    @Then("表示文が {string}")
    public void 表示文が(String text) {
        driver.expectPageToContainText(text);
    }
}

package cucumber.steps;

import cucumber.api.java.en.Then;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class FinalScoreSteps {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Then("^メッセージ欄に\"([^\"]*)\"が表示される$")
    public void メッセージ欄にが表示される(String message) {
        driver.expectElementToContainText("#message", message);
    }

    @Then("^分母に(\\d+)が表示される$")
    public void 分母にが表示される(int totalCount) {
        driver.expectElementToContainText("#total-count", String.valueOf(totalCount));
    }

    @Then("^分子に(\\d+)が表示される$")
    public void 分子にが表示される(int correctCount) {
        driver.expectElementToContainText("#correct-count", String.valueOf(correctCount));
    }

    @Then("^正答率に(\\d+)が表示される$")
    public void 正答率にが表示される(int correctPercentage) {
        driver.expectElementToContainText("#correct-percentage", String.valueOf(correctPercentage));
    }

    @Then("^苦手カテゴリーのメッセージ欄に\"([^\"]*)\"が表示される$")
    public void 苦手カテゴリーのメッセージ欄にが表示される(String exceptedMessage) {

        driver.expectElementToContainText("#category-message", exceptedMessage);
    }

}

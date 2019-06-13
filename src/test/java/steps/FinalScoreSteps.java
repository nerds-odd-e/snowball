package steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.onlinetest.Category;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.assertEquals;

public class FinalScoreSteps {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @And("^User answered (\\d+) questions wrong$")
    public void userAnsweredWrongTheThQuestionPage(int wrongQuestionNum) {
        for (int i = 0; i < wrongQuestionNum; ++i) {
            driver.clickRadioButton("wrongOption");
            driver.click("#answer");
            driver.click("#next");
        }
    }

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

    @Given("^\"([^\"]*)\" から (\\d+) 題出題される$")
    public void から題出題される(String category, int numberOfQuestions) {
        for (int i = 0; i < numberOfQuestions; i++)
            new QuestionBuilder()
                    .aQuestion(repo(Category.class).findFirstBy("name", category))
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();
        site.visit(String.format("onlinetest/launchQuestion?question_count=%d", numberOfQuestions));
    }

}

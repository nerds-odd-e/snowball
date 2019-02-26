package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.UiElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FinalScoreSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @And("^User answered wrong the (\\d+) th question page$")
    public void userAnsweredWrongTheThQuestionPage(int wrongQuestionNum) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        for (int i = 0; i < wrongQuestionNum; ++i) {
            driver.clickRadioButton("wrongOption");
            driver.clickButton("answer");
            driver.clickButton("next");
        }
    }

    @Then("^メッセージ欄に\"([^\"]*)\"が表示される$")
    public void メッセージ欄にが表示される(String message) throws Throwable {
        UiElement element = driver.findElementById("message");
        assertEquals(message, element.getText());
    }

    @Then("^分母に(\\d+)が表示される$")
    public void 分母にが表示される(int totalCount) throws Throwable {
        UiElement element = driver.findElementById("total-count");
        assertEquals(String.valueOf(totalCount), element.getText());
    }

    @Then("^分子に(\\d+)が表示される$")
    public void 分子にが表示される(int correctCount) throws Throwable {
        UiElement element = driver.findElementById("correct-count");
        assertEquals(String.valueOf(correctCount), element.getText());
    }

    @Then("^正答率に(\\d+)が表示される$")
    public void 正答率にが表示される(int correctPercentage) throws Throwable {
        UiElement element = driver.findElementById("correct-percentage");
        assertEquals(String.valueOf(correctPercentage), element.getText());
    }

    @Then("^苦手カテゴリーのメッセージ欄に\"([^\"]*)\"が表示される$")
    public void 苦手カテゴリーのメッセージ欄にが表示される(String exceptedMessage) throws Throwable {
        UiElement categoryMessages = driver.findElementById("category-message");

        assertEquals(exceptedMessage, categoryMessages.getText());
    }

    @Given("^\"([^\"]*)\" から (\\d+) 題出題される$")
    public void から題出題される(String category, int numberOfQuestions) throws Throwable {
        for (int i = 0; i < numberOfQuestions; i++)
            new QuestionBuilder()
                    .aQuestion(category)
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();
        site.visit(String.format("onlinetest/launchQuestion?question_count=%d", numberOfQuestions));
    }

    @Then("^終了ページにアドバイスエリアが表示されていない$")
    public void 終了ページにアドバイスエリアが表示されていない() throws Throwable {
        site.visit("onlinetest/end_of_test.jsp");
        assertFalse(driver.getBodyHTML().contains("advice_div"));
    }

    @Given("^最終ページが開かれている$")
    public void 最終ページが開かれている() throws Throwable {
        site.visit("onlinetest/end_of_test.jsp");
    }

    @Given("^test with categories \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void test_with_categories_and(String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I do \"([^\"]*)\" (\\d+)% correct$")
    public void i_do_correct(String arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see \"([^\"]*)\"'s advice as \"([^\"]*)\"$")
    public void i_should_see_s_advice_as(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see \"([^\"]*)\"'s rate as (\\d+)$")
    public void i_should_see_s_rate_as(String arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}

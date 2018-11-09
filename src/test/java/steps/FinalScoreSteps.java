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

public class FinalScoreSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^アドミンがスクラムのカテゴリの問題が(\\d+)問つくられている$")
    public void アドミンがスクラムのカテゴリの問題が問つくられている(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^ユーザが(\\d+)問正答する$")
    public void ユーザが問正答する(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\"が表示されてる事$")
    public void が表示されてる事(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

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
}

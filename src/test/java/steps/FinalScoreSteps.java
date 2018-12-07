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

    @Given("^\"([^\"]*)\" から (\\d+) 題、\"([^\"]*)\" から (\\d+) 題出題される$")
    public void からNumber_of_questions題からNumber_of_questions題出題される(String category1, int numberOfQuestions1, String category2, int numberOfQuestions2) throws Throwable {

        for (int i = 0; i < numberOfQuestions1; i++)
            new QuestionBuilder()
                    .aQuestion(category1)
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();

        for (int i = 0; i < numberOfQuestions2; i++)
            new QuestionBuilder()
                    .aQuestion(category2)
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();

        site.visit(String.format("onlinetest/launchQuestion?question_count=%d", numberOfQuestions1 + numberOfQuestions2));
    }

    @Given("^最終ページが開かれている$")
    public void 最終ページが開かれている() throws Throwable {
        site.visit("onlinetest/end_of_test.jsp");
    }

}

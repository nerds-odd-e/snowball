package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionCategorySteps {


    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^カテゴリー選択画面が表示される$")
    public void カテゴリー選択画面が表示される() throws Throwable {
        site.visit("onlinetest/question_category.jsp");
    }

    @Given("^クリアボタンが表示される$")
    public void クリアボタンが表示される() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^カテゴリーのチェックボックスにチェックが入っている$")
    public void カテゴリーのチェックボックスにチェックが入っている() throws Throwable {
        assertTrue(driver.findElements(By.cssSelector("input[type=checkbox]:checked")).size() > 0);
    }

    @When("^クリアボタンをクリック$")
    public void クリアボタンをクリック() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^カテゴリーのチェックボックスのチェックが全て外れる$")
    public void カテゴリーのチェックボックスのチェックが全て外れる() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^カテゴリーのチェックボックスにチェックが入っていない$")
    public void カテゴリーのチェックボックスにチェックが入っていない() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^スタートボタンをクリック$")
    public void スタートボタンをクリック() throws Throwable {
        driver.findElementById("start_test").click();
    }

    @Then("^質問画面に遷移しない$")
    public void 質問画面に遷移しない() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^\"([^\"]*)\"カテゴリーにQuestionが存在しない$")
    public void カテゴリーにquestionが存在しない(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^チェックボックスに\"([^\"]*)\"カテゴリーが表示されない$")
    public void チェックボックスに_カテゴリーが表示されない(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^問題画面へ遷移する$")
    public void 問題画面へ遷移する() throws Throwable {
        assertEquals("Question", driver.getCurrentTitle());
    }
}

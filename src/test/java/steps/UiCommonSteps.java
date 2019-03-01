package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiCommonSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^topページが表示されている$")
    public void topページが表示されている() throws Throwable {
        site.visit("ui_common.jsp");
    }

    @Given("^問題が存在している$")
    public void 問題が存在している() throws Throwable {
        new QuestionBuilder()
                .aQuestion("testDescription", "testAdvice", "scrum")
                .withWrongOption("Food")
                .withWrongOption("Drink")
                .withWrongOption("Country")
                .withWrongOption("Animal")
                .withCorrectOption("None of the above")
                .please();
    }

    @When("^Update Adviceのリンクをクリックする$")
    public void updateAdviceのリンクをクリックする() throws Throwable {
        driver.findElementById("update_advice").click();
    }

    @When("^StartTestのリンクをクリックする$")
    public void starttestのリンクをクリックする() throws Throwable {
        driver.findElementById("start_test").click();
    }

    @Then("^カテゴリ選択画面が表示される$")
    public void カテゴリ選択画面が表示される() throws Throwable {
        assertEquals("QuestionCategory", driver.getCurrentTitle());
    }

    @Then("^カテゴリのチェックボックスが表示される$")
    public void カテゴリのチェックボックスが表示される() throws Throwable {
        assertTrue(driver.findElements(By.cssSelector("input[type=\"checkbox\"]")).size() > 0);
    }

    @Then("^カテゴリのチェックボックスは全て選択されている$")
    public void カテゴリのチェックボックスは全て選択されている() throws Throwable {
        int totalElementCount = driver.findElements(By.cssSelector("input[type=checkbox]")).size();
        int elementCount = driver.findElements(By.cssSelector("input[type=checkbox]:checked")).size();
        assertEquals(totalElementCount, elementCount);
    }

    @Then("^startボタンが表示される$")
    public void startボタンが表示される() throws Throwable {
        assertEquals("start", driver.findElements(By.cssSelector("input[type=submit]")).get(0).getAttribute("value"));
    }

    @Then("^Update Adviceが表示される$")
    public void updateAdviceが表示される() throws Throwable {
        assertTrue(driver.getBodyHTML().contains("nav navbar-nav side-nav"));
        assertEquals("Update Advice", driver.getCurrentTitle());
    }
}

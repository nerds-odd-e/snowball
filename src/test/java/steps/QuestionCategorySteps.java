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

    @Given("^カテゴリーのチェックボックスにチェックが入っている$")
    public void カテゴリーのチェックボックスにチェックが入っている() throws Throwable {
        assertTrue(driver.findElements(By.cssSelector("input[type=checkbox]:checked")).size() > 0);
    }

    @When("^スタートボタンをクリック$")
    public void スタートボタンをクリック() throws Throwable {
        driver.findElementById("start_test").click();
    }

    @Then("^Question画面へ遷移する$")
    public void Question画面へ遷移する() throws Throwable {
        assertEquals("Question", driver.getCurrentTitle());
    }
}

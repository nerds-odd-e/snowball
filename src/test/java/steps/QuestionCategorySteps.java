package steps;

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

    @Given("^I'm on the category selection page$")
    public void カテゴリー選択画面が表示される() {
        site.visit("onlinetest/question_category.jsp");
    }

    @Given("^all the categories should have been selected$")
    public void カテゴリーのチェックボックスにチェックが入っている() {
        assertTrue(driver.findElements(By.cssSelector("input[type=checkbox]:checked")).size() > 0);
    }

    @When("^I click the start test button$")
    public void スタートボタンをクリック() {
        driver.findElementById("start_test").click();
    }

    @Then("^I should see the test starts$")
    public void 問題画面へ遷移する() {
        assertEquals("Question", driver.getCurrentTitle());
    }
}

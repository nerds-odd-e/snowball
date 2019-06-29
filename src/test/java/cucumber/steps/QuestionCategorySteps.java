package cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionCategorySteps {


    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^I'm on the category selection page$")
    public void カテゴリー選択画面が表示される() {
        site.visit("onlinetest/question_category.jsp");
    }

    @Given("^all the categories should have been selected$")
    public void カテゴリーのチェックボックスにチェックが入っている() {
        driver.expectElementToExist("input[type=checkbox]:checked");
    }

    @When("^I click the start test button$")
    public void スタートボタンをクリック() {
        driver.click("#start_test");
    }

    @Then("^I should see the test starts$")
    public void 問題画面へ遷移する() {
        driver.expectTitleToBe("Question");
    }
}

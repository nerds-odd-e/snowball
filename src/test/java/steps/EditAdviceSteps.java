package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

import static org.junit.Assert.*;

public class EditAdviceSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^I'm on the Update Advice page$")
    public void update_Adviceを開いている() {
        site.visit("onlinetest/category/advice");
    }

    @When("^I select category \"([^\"]*)\"$")
    public void 任意のカテゴリを選択している(String category) {
        driver.setDropdownByText("category", category);
    }

    @When("^I set the advice as \"([^\"]*)\"$")
    public void 任意のアドバイスを入力する(String advice) {
        driver.setTextField("advice", advice);
    }

    @And("^I set the advice link as \"([^\"]*)\"$")
    public void アドバイスリンクにと入力する(String link) {
        driver.setTextField("link", link);
    }

    @Then("^the advice link for \"([^\"]*)\" has become \"([^\"]*)\"$")
    public void updateAdvice画面に戻ってきてカテゴリがでアドバイスリンクがになってる(String category, String link) {
        assertTrue(driver.getBodyText().contains(category));
        assertTrue(driver.getBodyText().contains(link));
    }

    @When("^I click the update button$")
    public void updateボタンを押す() {
        driver.clickButtonByName("update");
    }

    @Then("^I should see the advice for \"([^\"]*)\" has become \"([^\"]*)\"$")
    public void update_Advice画面に戻ってきてアドバイスが更新されている(String category, String updatedAdvice) {
        driver.setDropdownByText("category", category);
        assertTrue(driver.getBodyText().contains(updatedAdvice));
    }
}

package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.*;

public class EditAdviceSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^Update Adviceを開いている$")
    public void update_Adviceを開いている() throws Throwable {
        site.visit("onlinetest/category/advice");
    }

    @When("^カテゴリで\"([^\"]*)\"を選択している$")
    public void 任意のカテゴリを選択している(String category) throws Throwable {
        driver.setDropdownByText("category", category);
    }

    @When("^アドバイスに\"([^\"]*)\"と入力する$")
    public void 任意のアドバイスを入力する(String advice) throws Throwable {
        driver.setTextField("advice", advice);
    }

    @When("^Updateボタンを押す$")
    public void updateボタンを押す() throws Throwable {
        driver.clickButtonByName("update");
    }

    @Then("^Update Advice画面に戻ってきてカテゴリが\"([^\"]*)\"でアドバイスが\"([^\"]*)\"になってる$")
    public void update_Advice画面に戻ってきてアドバイスが更新されている(String category, String updatedAdvice) throws Throwable {
        driver.setDropdownByText("category", category);
        assertTrue(driver.getBodyText().contains(updatedAdvice));
    }
}

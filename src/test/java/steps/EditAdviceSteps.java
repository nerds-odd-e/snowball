package steps;

import com.odde.massivemailer.model.onlinetest.Category;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.*;

public class EditAdviceSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private String categoryName;
    private String advice;

    @Given("^Update Adviceを開いている$")
    public void update_Adviceを開いている() throws Throwable {
        site.visit("onlinetest/category/advice");
    }

    @When("^カテゴリで\"([^\"]*)\"を選択している$")
    public void 任意のカテゴリを選択している(String category) throws Throwable {
        this.categoryName = category;
        driver.setDropdownByText("category", category);
    }

    @When("^アドバイスに\"([^\"]*)\"と入力する$")
    public void 任意のアドバイスを入力する(String advice) throws Throwable {
        this.advice = advice;
        driver.setTextField("advice", advice);
    }

    @When("^Updateボタンを押す$")
    public void updateボタンを押す() throws Throwable {
        driver.clickButtonByName("update");
        assertEquals("Update Advice",driver.getCurrentTitle());
    }

    @Then("^Update Advice画面に戻ってきてカテゴリが\"([^\"]*)\"でアドバイスが\"([^\"]*)\"になってる$")
    public void update_Advice画面に戻ってきてカテゴリが_でアドバイスが_になってる(String category, String advice) throws Throwable {
        site.visit("onlinetest/category/advice?category=" + category);
        driver.setDropdownByText("category", category);
        assertEquals(advice, driver.findElementByName("advice").getText());
    }
}

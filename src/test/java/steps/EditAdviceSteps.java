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
        site.visit("onlinetest/edit_category_advice.jsp");
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
    }

    @Then("^画面に入力した内容が画面に表示されている$")
    public void 画面に入力した内容が画面に表示されている() throws Throwable {
        site.visit("onlinetest/edit_category_advice.jsp");
        driver.setDropdownByText("category", this.categoryName);
        assertEquals(this.advice, driver.findElementByName("advice").getText());
    }
}

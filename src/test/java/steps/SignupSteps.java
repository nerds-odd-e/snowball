package steps;

import com.odde.massivemailer.model.User;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class SignupSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String signup_url = site.baseUrl() + "signup.jsp";
    private final String tokkun_url = site.baseUrl() + "tokkun/top";

    @Given("^ユーザーがサインアップページを開いている$")
    public void ユーザーがサインアップページを開いている() throws Throwable {
        driver.visit(signup_url);
    }

    @When("^ユーザーはUserNameに\"([^\"]*)\"を入力する$")
    public void ユーザーはUserNameに_を入力する(String userName) throws Throwable {
        driver.setTextField("userName", userName);
    }

    @When("^ユーザーはEmailに\"([^\"]*)\"を入力する$")
    public void ユーザーはemailに_を入力する(String email) throws Throwable {
        driver.setTextField("email", email);
    }

    @When("^ユーザーはPasswordに\"([^\"]*)\"を入力する$")
    public void ユーザーはpasswordに_を入力する(String password) throws Throwable {
        driver.setTextField("password", password);
    }

    @When("^ユーザーはPassword_confirmに\"([^\"]*)\"を入力する$")
    public void ユーザーはpassword_confirmに_を入力する(String password) throws Throwable {
        driver.setTextField("password_confirm", password);
    }

    @When("^ユーザーはSubmitボタンをクリックする$")
    public void ユーザーはsubmitボタンをクリックする() throws Throwable {
        driver.clickButton("signup");
    }
    @Then("^特訓のトップページに遷移する$")
    public void 特訓のトップページに遷移する() throws Throwable {
        String expected = tokkun_url;
        assertEquals(expected, driver.getCurrentUrl());
    }

    @Then("^自分のユーザー名\"([^\"]*)\"が表示されている$")
    public void 自分のユーザー名_が表示されている(String username) throws Throwable {
        String actualUserName = driver.findElementById("user_name").getText();
        assertEquals(username, actualUserName);
    }

    @Then("^サインアップページにエラーメッセージが表示される$")
    public void サインアップページにエラーメッセージが表示される() throws Throwable {
        assertTrue(driver.getBodyText().contains("Signup failed"));
    }


    @Given("^ユーザー\"([^\"]*)\"がユーザー登録済みである$")
    public void ユーザー_がユーザー登録済みである(String username) throws Throwable {
        // register some user
        User.deleteAll();
        User user = new User(username + "@example.com");
        user.setUserName(username);
        user.setPassword("testpassword");
        user.saveIt();
    }

}

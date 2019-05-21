package steps;

import com.odde.massivemailer.model.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SignupSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String signup_url = site.baseUrl() + "signup.jsp";
    private final String dashboard_url = site.baseUrl() + "dashboard";

    @Given("^user is on the sign up page$")
    public void ユーザーがサインアップページを開いている() {
        driver.visit(signup_url);
    }

    @When("^ユーザーはUserNameに\"([^\"]*)\"を入力する$")
    public void ユーザーはUserNameに_を入力する(String userName) {
        driver.setTextField("userName", userName);
    }

    @When("^ユーザーはEmailに\"([^\"]*)\"を入力する$")
    public void ユーザーはemailに_を入力する(String email) {
        driver.setTextField("email", email);
    }

    @When("^ユーザーはPasswordに\"([^\"]*)\"を入力する$")
    public void ユーザーはpasswordに_を入力する(String password) {
        driver.setTextField("password", password);
    }

    @When("^ユーザーはPassword_confirmに\"([^\"]*)\"を入力する$")
    public void ユーザーはpassword_confirmに_を入力する(String password) {
        driver.setTextField("password_confirm", password);
    }

    @When("^ユーザーはSubmitボタンをクリックする$")
    public void ユーザーはsubmitボタンをクリックする() {
        driver.clickButton("signup");
    }

    @Then("^特訓のトップページに遷移する$")
    public void 特訓のトップページに遷移する() {
        String expected = dashboard_url;
        assertEquals(expected, driver.getCurrentUrl());
    }

    @Then("^自分のユーザー名\"([^\"]*)\"が表示されている$")
    public void 自分のユーザー名_が表示されている(String username) {
        String actualUserName = driver.findElementById("user_name").getText();
        assertEquals(username, actualUserName);
    }

    @Then("^サインアップページにエラーメッセージが表示される$")
    public void サインアップページにエラーメッセージが表示される() {
        assertTrue(driver.getBodyText().contains("Signup failed"));
    }


    @Given("^ユーザー\"([^\"]*)\"がユーザー登録済みである$")
    public void ユーザー_がユーザー登録済みである(String username) {
        // register some user
        User.deleteAll();
        User user = new User(username + "@example.com");
        user.setUserName(username);
        user.setPassword("testpassword");
        user.saveIt();
    }

    @When("^user sign up with:$")
    public void userSignUpWith(Map<String, String> table) {
        table.forEach((k, v)-> driver.setTextField(k, v));
    }
}

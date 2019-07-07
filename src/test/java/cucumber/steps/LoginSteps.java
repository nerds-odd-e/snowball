package cucumber.steps;

import com.odde.snowball.model.User;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.*;

public class LoginSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String login_url = site.baseUrl() + "login.jsp";

    private void visitLoginPage() {
        driver.visit(login_url);
        driver.expectPageToContainText("Login Massive Mailer");
    }

    @Given("^Login failed message is not shown$")
    public void login_failed_message_is_not_shown() {
        driver.expectPageNotToContainText("login failed");
    }

    @Given("^There is a user with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void there_is_a_user_with_and(String email, String password) {
        User user = new User(email);
        user.setupPassword(password);
        user.save();
    }

    @Given("^There is a user with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void there_is_a_user(String email, String password) {
        User user = new User(email);
        user.setupPassword(password);
        user.save();
    }

    @Given("^I login with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void login_in_with_email_and_pwd(String email, String password) {
        visitLoginPage();
        driver.setTextField("email", email);
        driver.setTextField("password", password);
        driver.click("#login");
    }

    @Given("^user hasn't logged in$")
    public void ユーザーは未ログイン() {
    }

    @When("^visiting dashboard$")
    public void 特訓トップにアクセスする() {
        site.visit("dashboard");
    }

    @Then("^user should be redirected to login page$")
    public void ログインページに遷移する() {
        driver.expectTitleToBe("Login");
    }

    @Then("^user should see the dashboard$")
    public void show_course_list_of_current_user() {
        driver.expectURLToContain("dashboard");
    }

    @Then("^I should move to page with url \"([^\"]*)\"$")
    public void i_should_move_to_page_with_url(String url) {
        String expected = site.baseUrl() + url;
        driver.expectURLToContain(expected);
    }

    @Given("^There is a user with \"([^\"]*)\" but password initialize is undone$")
    public void there_is_a_user_with_but_password_initialize_is_undone(String email) {
        User user = new User(email);
        user.save();
    }

    @And("^Login failed message is shown$")
    public void login_failed_message_is_shown() {
        driver.expectPageToContainText("login failed");
    }

    @Then("^Login failed message is hidden$")
    public void login_failed_message_is_hidden() {
        driver.expectPageNotToContainText("login failed");
    }

    @Then("^Show courses list \"([^\"]*)\"$")
    public void show_courses_list(String courses) {
        driver.expectPageToContainText("Course List");
        if (StringUtils.isNotEmpty(courses)) {
            Arrays.asList(courses.split(",")).forEach(driver::expectPageToContainText);
        }
    }

    @Given("^I move to top page$")
    public void i_move_to_top_page() {
        site.visit("index.html");
    }

    @When("^I move to course list page$")
    public void i_move_to_course_list_page() {
        site.visit("course_list.jsp");
    }

    @Then("^Show all courses list \"([^\"]*)\"$")
    public void show_all_courses_list(String courses) {
        show_courses_list(courses);
    }

    @When("^click the sign up link from the login page$")
    public void ログインページのサインアップページのリンクを押下する() {
        visitLoginPage();
        driver.click("#signup");
    }

    @Then("^user should see the sign up page$")
    public void サインアップページに遷移すること() {
        driver.expectTitleToBe("Sign Up");
    }

    @Given("user {string} has logged in successfully")
    public void userHasLoggedInSuccessfully(String username) {
        String email = username + "@email.com";
        String pwd = username + "pwd";
        there_is_a_user(email, pwd);
        login_in_with_email_and_pwd(email, pwd);
    }
}

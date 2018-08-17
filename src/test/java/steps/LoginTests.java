package steps;

import com.odde.massivemailer.model.User;
import cucumber.api.DataTable;
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

import static org.junit.Assert.*;

public class LoginTests{
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    private String login_url = site.baseUrl() + "login.jsp";

    @Given("^Visit Login Page$")
    public void visitLoginPage() throws Throwable {
        driver.visit(login_url);
        driver.pageShouldContain("Login Massive Mailer");
    }

    @Given("^Login failed message is not shown$")
    public void login_failed_message_is_not_shown() throws Throwable {
        assertFalse(driver.getBodyText().contains("login failed"));
    }

    @Given("^There is a user with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void there_is_a_user_with_and(String email, String password) throws Throwable {
        User.deleteAll();
        User user = new User(email);
        user.setPassword(password);
        user.saveIt();
    }

    @Given("^There are users as bellow$")
    public void there_are_users_as_bellow(DataTable userTable) throws Throwable {
        User.deleteAll();
        Map<String, String> vals = userTable.asMap(String.class, String.class);
        vals.entrySet().forEach(entry -> {
            User user = new User(entry.getKey());
            user.setPassword(entry.getValue());
            user.saveIt();
        });
    }

    @Given("^Fill form with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void fill_form_with_and(String arg1, String arg2) throws Throwable {
        driver.setTextField("email", arg1);
        driver.setTextField("password", arg2);
    }

    @When("^I click login button$")
    public void i_click_login_button() throws Throwable {
        driver.clickButton("login");
    }

    @Then("^Show course list of current user$")
    public void show_course_list_of_current_user() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("course_list"));
    }

    @Then("^I should move to page with url \"([^\"]*)\"$")
    public void i_should_move_to_page_with_url(String arg1) throws Throwable {
        String expected = site.baseUrl() + arg1;
        assertEquals(expected, driver.getCurrentUrl());
    }

    @Given("^There is a user with \"([^\"]*)\" but password initialize is undone$")
    public void there_is_a_user_with_but_password_initialize_is_undone(String email) throws Throwable {
        User.deleteAll();
        User user = new User(email);
        user.saveIt();
    }

    @And("^Login failed message is shown$")
    public void login_failed_message_is_shown() throws Throwable {
        driver.pageShouldContain("login failed");
    }

    @Then("^Login failed message is hidden$")
    public void login_failed_message_is_hidden() throws Throwable {
        assertFalse(driver.getBodyText().contains("login failed"));
    }

    @Then("^Show courses list \"([^\"]*)\"$")
    public void show_courses_list(String courses) throws Throwable {
        driver.pageShouldContain("Course List");

        List<String> expected  = new ArrayList<>();
        if (StringUtils.isNotEmpty(courses)) {
            expected = new ArrayList<>(Arrays.asList(courses.split(",")));
        }

        List<String> actual = new ArrayList<>();
        for (WebElement e : driver.findElements(By.className("course-name"))) {
            actual.add(e.getText().split(" - ")[1]);
        }
        Collections.sort(expected);
        Collections.sort(actual);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Given("^I move to top page$")
    public void i_move_to_top_page() throws Throwable {
        site.visit("index.html");
    }

    @When("^I move to course list page$")
    public void i_move_to_course_list_page() throws Throwable {
        site.visit("course_list.jsp");
    }


}

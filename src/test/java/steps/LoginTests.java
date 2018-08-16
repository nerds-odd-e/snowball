package steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import com.odde.massivemailer.model.User;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Given("^There is a user with \"([^\"]*)\" and \"([^\"]*)\" and password initialize is done$")
    public void there_is_a_user_with_and_and_password_initialize_is_done(String email, String password) throws Throwable {
        User.deleteAll();
        User user = new User(email);
        user.setPassword(password);
        user.saveIt();
    }

    @Given("^There is a user with \"([^\"]*)\" and \"([^\"]*)\" and password initialize is undone$")
    public void there_is_a_user_with_and_and_password_initialize_is_undone(String email, String password) throws Throwable {
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
        List<String> expected = Arrays.asList(courses.split(","));

        List<String> courseListOnPage = new ArrayList<String>();
        for (WebElement e : driver.findElements(By.className("course-name"))) {
            courseListOnPage.add(e.getText().split(" - ")[1]);
        }
        Collections.sort(expected);
        Collections.sort(courseListOnPage);

        assertEquals(expected, courseListOnPage);
    }

    @Then("^Matsuo Show cources list test \"([^\"]*)\"$")
    public void show_cources_list_test(String cources) throws Throwable {
        driver.visit(site.baseUrl() + "course_list.jsp");
        String[] expected = cources.split(",");

        System.out.println(driver.findElementById("page-wrapper").getText());

        List<String> courseListOnPage = new ArrayList<String>();
        for (WebElement e : driver.findElements(By.className("course"))) {
            System.out.println(e.getText());
            courseListOnPage.add(e.getText());
        }
//
//        String[] actual = {};
//        actual = courseListOnPage.toArray(actual);
//        assertArrayEquals(expected, actual);
//
//        driver.pageShouldContain("Course List");

    }
}

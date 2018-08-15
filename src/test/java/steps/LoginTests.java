package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTests {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    private String login_url = site.baseUrl() + "login.jsp";

    @Given("^Visit Login Page$")
    public void visitLoginPage() throws Throwable {
        driver.visit(login_url);
        driver.pageShouldContain("Login Massive Mailer");
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
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should move to page with url \"([^\"]*)\"$")
    public void i_should_move_to_page_with_url(String arg1) throws Throwable {
        String expected = site.baseUrl() + arg1;
        assertEquals(expected, driver.getCurrentUrl());
    }

    @Then("^Login failed message is shown$")
    public void login_failed_message_is_shown() throws Throwable {
        driver.pageShouldContain("login failed");
    }

    @Then("^Show cources list \"([^\"]*)\"$")
    public void show_cources_list(String cources) throws Throwable {
        String[] expected = cources.split(",");
        List<String> courseListOnPage = new ArrayList<String>();
        for (WebElement e : driver.findElements(By.className("course name"))) {
            courseListOnPage.add(e.getText());
        }

        String[] actual = {};
        courseListOnPage.toArray(actual);
        assertArrayEquals(actual, expected);

        driver.pageShouldContain("Course List");
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

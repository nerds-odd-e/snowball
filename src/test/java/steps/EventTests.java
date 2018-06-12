package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class EventTests {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @Given("^I am on Add Event page$")
    public void visitAddEventPage() throws Throwable {
        site.visit("add_course.jsp");
    }

    @When("^Create course \"([^\"]*)\" in \"([^\"]*)\"$")
    public void addEventAndSelectLocationFromDropdown(String courseName, String location, String date) throws Throwable {
        driver.setTextField("coursename", courseName);
        driver.setTextField("coursedetails", "nothing important");
        driver.setTextField("startdate", date);
        driver.setDropdownValue("location", location);
    }

    public void addEventAndSelectLocationFromDropdownAndTextBox(String courseName, String country, String city, String date) throws Throwable {
        driver.setTextField("coursename", courseName);
        driver.setTextField("coursedetails", "nothing important");
        driver.setTextField("startdate", date);
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
    }

    @When("^I click the save button$")
    public void clickRegisterEvent() throws Throwable {
        driver.clickButton("save_button");
    }

    @Then("^Course save page should display \"([^\"]*)\"$")
    public void eventListPageShouldContain(String message) throws Throwable {
        driver.pageShouldContain(message);
    }

    void addCourse(String courseName, String location, String date) throws Throwable {
        visitAddEventPage();
        addEventAndSelectLocationFromDropdown(courseName, location, date);
        clickRegisterEvent();
    }

    void addCourseWithCountryAndCity(String courseName, String country, String city, String date) throws Throwable {
        visitAddEventPage();
        addEventAndSelectLocationFromDropdownAndTextBox(courseName, country, city, date);
        clickRegisterEvent();
    }
}

package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

public class EventTests {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^I am on Add Event page$")
    public void visitAddEventPage() {
        site.visit("add_course.jsp");
    }

    @When("^Create course \"([^\"]*)\" in \"([^\"]*)\"$")
    public void addEventAndSelectLocationFromDropdown(String courseName, String location, String date) {
        driver.setTextField("courseName", courseName);
        driver.setTextField("courseDetails", "nothing important");
        driver.setTextField("startDate", date);
        driver.setDropdownValue("location", location);
    }

    private void addEventAndSelectLocationFromDropdownAndTextBox(String courseName, String country, String city, String date) {
        driver.setTextField("courseName", courseName);
        driver.setTextField("courseDetails", "nothing important");
        driver.setTextField("startDate", date);
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
    }

    @When("^I click the save button$")
    public void clickRegisterEvent() {
        driver.clickButton("save_button");
    }

    @Then("^Course save page should display \"([^\"]*)\"$")
    public void eventListPageShouldContain(String message) {
        driver.pageShouldContain(message);
    }

    void addCourseWithCountryAndCity(String courseName, String country, String city, String date) {
        visitAddEventPage();
        addEventAndSelectLocationFromDropdownAndTextBox(courseName, country, city, date);
        clickRegisterEvent();
    }

}

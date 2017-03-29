package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class EventTests {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/add_event.jsp";
    private String EVENTLIST_BASE_URL = "http://localhost:8070/massive_mailer/eventlist.jsp";

    @Given("^I am on Add Event page$")
    public void visitAddEventPage() throws Throwable {
        driver.visit(BASE_URL);
    }

    @When("^Add an event \"([^\"]*)\" at \"([^\"]*)\"$")
    public void clickRegisterEvent(String eventTitle,String location) throws Throwable {
        driver.text_field("evtTitle", eventTitle);
        driver.text_field("location", location);
        driver.click_button("register_button");
    }

    @Then("^Event list page should contain \"([^\"]*)\" and \"([^\"]*)\"$")
    public void eventListPageShouldContain(String eventTitle,String eventLocation) throws Throwable {
        driver.expectRedirect(EVENTLIST_BASE_URL);
        driver.pageShouldContain(eventTitle);
        driver.pageShouldContain(eventLocation);
    }
}

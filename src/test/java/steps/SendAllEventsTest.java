package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import java.util.List;

public class SendAllEventsTest {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/eventlist.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private int numberOfEvents, numberOfEventsInLocation;
    private int  numberOfContacts, numberOfContactsInLocation;

    @Given("^visit event list page$")
    public void VisitEventListPage() throws Throwable {
        this.numberOfEvents = 0;
        this.numberOfContacts = 0;
        driver.visit(BASE_URL);
    }


    @When("^(\\d+) out of (\\d+) contacts are in Singapore$")
    public void numberOfContactIs(int numberOfContactsInLocation, int numOfContacts) throws Throwable {
        this.numberOfContactsInLocation = numberOfContactsInLocation;
        this.numberOfContacts = numOfContacts;
        MyStepdefs contactTests = new MyStepdefs();
        for (int i=0;i<this.numberOfContacts;i++) {
            contactTests.addAContact("test@test"+i+".com","Singapore");
        }
    }

    @When("^We have below number of contacts at each location:$")
    public void createContactsForLocations(DataTable dtContactsPerLocation) throws Throwable {
        List<List<String>> contacts = dtContactsPerLocation.raw();
        contacts = contacts.subList(1, contacts.size());//skip header row
        int totalNumberOfContacts = 0;
        for(List<String> location:contacts){
            MyStepdefs contactTests = new MyStepdefs();
            this.numberOfContactsInLocation = Integer.parseInt(location.get(1));
            for (int i=0;i<this.numberOfContactsInLocation;i++) {
                totalNumberOfContacts++;
                contactTests.addAContact("test@test"+totalNumberOfContacts+".com",location.get(0));
            }
        }
        this.numberOfContacts = totalNumberOfContacts;
    }

    @When("^(\\d+) out of (\\d+) events are in Singapore$")
    public void numberOfEventIs(int numberOfEventsInLocation, int numberOfEvents) throws Throwable {
        this.numberOfEventsInLocation = numberOfEventsInLocation;
        this.numberOfEvents = numberOfEvents;
        EventTests eventTests = new EventTests();
        for(int i=0;i<this.numberOfEventsInLocation;i++) {
            eventTests.visitAddEventPage();
            eventTests.addEventAndSelectLocationFromDropdown("Event "+i,"Singapore");
            eventTests.clickRegisterEvent();
        }
        for(int i=0;i<this.numberOfEvents-this.numberOfEventsInLocation;i++) {
            eventTests.visitAddEventPage();
            eventTests.addEventAndSelectLocationFromDropdown("Event "+i,"Not-Singapore");
            eventTests.clickRegisterEvent();
        }
    }

    @When("^We have below number of events at each location:$")
    public void createEventsForLocations(DataTable dtEventsPerLocation) throws Throwable {
        List<List<String>> events = dtEventsPerLocation.raw();
        events = events.subList(1, events.size());//skip header row
        int totalNumberOfEvent = 0;
        for(List<String> oneLocation:events){
            EventTests eventTests = new EventTests();
            this.numberOfEventsInLocation = Integer.parseInt(oneLocation.get(1));
            for (int i=0;i<this.numberOfEventsInLocation;i++) {
                totalNumberOfEvent++;
                eventTests.visitAddEventPage();
                eventTests.addEventAndSelectLocationFromDropdown("Event "+totalNumberOfEvent,oneLocation.get(0));
                eventTests.clickRegisterEvent();
            }
        }
        this.numberOfEvents = totalNumberOfEvent;
    }


    @When("^I click send button$")
    public void ClickSendButton() throws Throwable {
        driver.visit(BASE_URL);
        driver.clickButton("send_button");
    }

    @Then("^([^\"]*) contact\\(s\\) receive an email that contains ([^\"]*)$")
    public void contactReceiveEmailContainsEvents(String numberOfEmailRecipients, String numberOfEventsInEmail) throws Throwable {
        String expectedMessage = String.format("%s emails contain %s events sent.", numberOfEmailRecipients, numberOfEventsInEmail);
        driver.expectElementWithIdToContainText("message", expectedMessage);
    }

    @Then("It should send out emails:")
    public void shouldSendOutEmails(DataTable dtEmails){
        List<List<String>> emails = dtEmails.raw();
        emails = emails.subList(1, emails.size());//skip header row
        for(List<String> oneLocation:emails) {
            String expectedMessage = String.format("%s emails contain %s events sent.", oneLocation.get(1), oneLocation.get(2));
            driver.expectElementWithIdToContainText("message", expectedMessage);
        }
    }
}

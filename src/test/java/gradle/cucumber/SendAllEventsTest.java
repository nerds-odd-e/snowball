package gradle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SendAllEventsTest {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/eventlist.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private int numberOfEvents;
    private int  numberOfContacts;

    @Given("^visit event list page$")
    public void VisitEventListPage() throws Throwable {
        Class.forName("org.sqlite.JDBC");
        driver.visit(BASE_URL);
    }

    @When("^number of contact is \"([^\"]*)\"$")
    public void numberOfContactIs(String numberOfContacts) throws Throwable {
        this.numberOfContacts = Integer.parseInt(numberOfContacts);
    }

    @When("^number of event is \"([^\"]*)\"$")
    public void numberOfEventIs(String numberOfEvents) throws Throwable {
        this.numberOfEvents = Integer.parseInt(numberOfEvents);
    }

    @When("^I click send button$")
    public void ClickSendButton() throws Throwable {
        deleteAllContacts();
        deleteAllEvents();

        for (int i = 0; i < numberOfContacts; i++) {
            createContact("Sample contact " + (i + 1), "sample-" + (i + 1) + "@example.com");
        }

        for (int i = 0; i < numberOfEvents; i++) {
            createEvent("Sample event " + (i + 1));
        }

        driver.click_button("send_button");
    }

    @Then("^([^\"]*) contact\\(s\\) receive an email that contains ([^\"]*)$")
    public void contactReceiveEmailContainsEvents(String numberOfEmailRecipients, String numberOfEventsInEmail) throws Throwable {
        String expectedMessage = String.format("%s emails contain %s events sent.", numberOfEmailRecipients, numberOfEventsInEmail);
        driver.expectElementWithIdToContainText("message", expectedMessage);
    }

    private void deleteAllEvents() {
        executeSqlStatement("DELETE FROM event");
    }

    private void createEvent(String eventName) {
        executeSqlStatement(String.format("INSERT INTO event (title) VALUES ('%s')", eventName));
    }

    private void deleteAllContacts() {
        executeSqlStatement("DELETE FROM mail");
    }

    private void createContact(String contactName, String emailAddress) {
        executeSqlStatement(
                String.format("INSERT INTO mail (name, email) VALUES ('%s', '%s')", contactName, emailAddress)
        );
    }

    private void executeSqlStatement(String sqlStatement) {
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        Properties properties = sqLiteConfig.toProperties();
        properties.setProperty(SQLiteConfig.Pragma.DATE_STRING_FORMAT.pragmaName, "yyyy-MM-dd HH:mm:ss");

        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:oddemail.db", properties);
                Statement statement = connection.createStatement();
        ) {
            int retunedValue = statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {}
    }
}

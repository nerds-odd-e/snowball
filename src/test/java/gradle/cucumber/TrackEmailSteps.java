package gradle.cucumber;

import com.odde.massivemailer.service.impl.SqliteBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class TrackEmailSteps {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    @Given("^I send an email to Terry$")
    public void i_send_an_email_to_Terry() throws Throwable {
        driver.visit(BASE_URL);
        driver.text_field("recipient", "terry@odd-e.com");
        driver.text_field("subject", "Subject");
        driver.text_field("content", "Hello!");

        driver.click_button("send_button");
    }

    @When("^Terry does not open the email$")
    public void terry_does_not_open_the_email() throws Throwable {
    }

    @Then("^I should see that Terry has not opened the email$")
    public void i_should_see_that_Terry_has_not_opened_the_email() throws Throwable {
        SqliteBase base = new SqliteBase();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            base.openConnection();

            String sql = "SELECT read_count FROM notification_details WHERE email_address = 'terry@odd-e.com'";

            ps = base.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                assertThat(count, is(0));
            } else {
                fail("No result?");
            }
        } finally {
            rs.close();
            ps.close();
            base.closeConnection();
        }
    }
}

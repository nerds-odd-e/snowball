package gradle.cucumber;

import com.odde.massivemailer.service.impl.SqliteBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class TrackEmailSteps {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    @Given("^I send an email to \"(.*)\"$")
    public void i_send_an_email_to_Terry(String recipient) throws Throwable {
        driver.visit(BASE_URL);
        driver.text_field("recipient", recipient);
        driver.text_field("subject", "Subject");
        driver.text_field("content", "Hello!");

        driver.click_button("send_button");
    }

    @When("^\"(.*)\" does not open the email$")
    public void terry_does_not_open_the_email(String receipient) throws Throwable {
    }

    @Then("^I should see that \"(.*)\" has not opened the email$")
    public void i_should_see_that_Terry_has_not_opened_the_email(String receipient) throws Throwable {
        int count = getNotificationDetailCount(receipient);

        assertThat(count, is(0));
    }

    @When("^\"(.*)\" open the email$")
    public void terry_open_the_email(String receipient) throws Throwable {
        SqliteBase base = new SqliteBase();

        PreparedStatement ps = null;
        ResultSet rs = null;

        Long notificationId = 0L;

        try {
            base.openConnection();

            String sql = "SELECT notification_id FROM notification_details WHERE email_address = ?";

            ps = base.getConnection().prepareStatement(sql);
            ps.setString(1, receipient);
            rs = ps.executeQuery();

            if (rs.next()) {
                notificationId = rs.getLong(1);
            } else {
                fail("No result?");
            }
        } finally {
            rs.close();
            ps.close();
            base.closeConnection();
        }

        String url = BASE_URL+"resources/images/qrcode.png?messageId="+notificationId+"&userId="+receipient;
        driver.visit(url);
    }

    @Then("^I should see that \"(.*)\" has opened the email$")
    public void i_should_see_that_Terry_has_opened_the_email(String receipient) throws Throwable {
        int count = getNotificationDetailCount(receipient);

        assertThat(count, is(1));
    }

    private int getNotificationDetailCount(String receipient) throws ClassNotFoundException, SQLException {
        SqliteBase base = new SqliteBase();

        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            base.openConnection();

            String sql = "SELECT read_count FROM notification_details WHERE email_address = ?";

            ps = base.getConnection().prepareStatement(sql);
            ps.setString(1, receipient);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                fail("No result?");
            }
        } finally {
            rs.close();
            ps.close();
            base.closeConnection();
        }
        return count;
    }
}

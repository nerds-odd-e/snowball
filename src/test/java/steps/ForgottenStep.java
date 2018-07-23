package steps;

import com.odde.massivemailer.model.ContactPerson;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ForgottenStep {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @When("^Enter contact list page$")
    public void enter_contact_list_page() {
        site.visit("contactlist.jsp");
    }

    @Then("^ivan@odde\\.com is displayed as red at the list below$")
    public void ivan_odde_com_is_displayed_as_red_at_the_list_below() {
        driver.pageShouldContain("ivan@odde.com");

        boolean existIvan = driver.findElements(By.id("forgotten_table"))
                .stream()
                .flatMap(it -> it.findElements(By.className("email-address")).stream())
                .filter(it -> it.getText().contains("ivan@odde.com"))
                .count() == 1;
        assertTrue("ivan@odde.com in the element with class 'contact_email', 'forgotten'", existIvan);
    }

    @Given("^Contact ivan@odde\\.com exists$")
    public void contactIvanOddeComExists() {
        String email = "ivan@odde.com";
        ContactPerson existingContact = ContactPerson.getContactByEmail(email);
        if (existingContact == null) {
            ContactPerson contact = new ContactPerson("ivan", email, "ivan");
            contact.saveIt();
        }
    }

}

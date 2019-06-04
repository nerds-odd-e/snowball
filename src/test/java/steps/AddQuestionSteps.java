package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;


public class AddQuestionSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();

    @Given("^Todo一覧ページに遷移$")
    public void todo一覧ページに遷移() {
        site.visit("/todos");
    }

    @Then("^Todo一覧ページが表示される$")
    public void todo一覧ページが表示される() {
        assertEquals(true, site.getDriver().getBodyText().contains("hello"));
    }
}

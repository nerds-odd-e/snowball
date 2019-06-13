package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.site.MassiveMailerSite;

public class SampleSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();


    @When("^Topページに遷移する$")
    public void topページに遷移する() {
        site.visit("/index.html");
    }

    @Then("^\"([^\"]*)\"が表示されている$")
    public void が表示されている(String text) {
        site.getDriver().pageShouldContain(text);
    }
}

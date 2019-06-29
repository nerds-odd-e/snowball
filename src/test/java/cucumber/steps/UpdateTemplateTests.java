package cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.List;

public class UpdateTemplateTests {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String sendemail_url = site.baseUrl() + "admin/sendemail.jsp";

    @Given("^Visit Edit Template Page$")
    public void visitEditTemplatePage() {
        driver.visit(sendemail_url);
    }

    @When("^I update the contents of template$")
    public void updateTemplateWithGivenDetails(List<List<String>> templates) {
        templates = templates.subList(1, templates.size());
        for (List<String> val : templates) {
            driver.selectDropdownByText("templateList", val.get(0));
            driver.setTextField("content", val.get(1));
        }
    }

    @When("^I click on update button$")
    public void clickUpdateTemplate() {
        driver.click("#update_button");
    }

    @Then("^Template contents should be update and I should get an element with message \"<success>\"$")
    public void shouldGetSuccessMessage() {
        driver.expectRedirect(sendemail_url);
    }
}

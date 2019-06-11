package steps;

        import cucumber.api.DataTable;
        import cucumber.api.java.en.Given;
        import cucumber.api.java.en.Then;
        import cucumber.api.java.en.When;
        import steps.driver.WebDriverWrapper;
        import steps.site.SnowballSite;

        import java.util.List;

public class UpdateTemplateTests {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String sendemail_url = site.baseUrl()+ "admin/sendemail.jsp";

    @Given("^Visit Edit Template Page$")
    public void visitEditTemplatePage() {
        driver.visit(sendemail_url);
    }

    @When("^I update the contents of template$")
    public void updateTemplateWithGivenDetails(DataTable templateDetails) {
        List<List<String>> templates = templateDetails.raw();
        templates = templates.subList(1, templates.size());
        for(List<String> val:templates){
            driver.setDropdownByText("templateList", val.get(0));
            driver.setTextField("content", val.get(1));
        }
    }

    @When("^I click on update button$")
    public void clickUpdateTemplate() {
        driver.clickButton("update_button");
    }

    @Then("^Template contents should be update and I should get an element with message \"<success>\"$")
    public void shouldGetSuccessMessage() {
        driver.expectRedirect(sendemail_url);
    }
}

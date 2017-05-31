package steps;

import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.service.TemplateService;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by csd on 31/5/17.
 */
public class DefaultTemplateTest {

    TemplateService templateService;
    Template template;

    @When("^Get default template$")
    public void get_default_template() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        templateService = new TemplateService();
        template = templateService.getDefaultTemplate("");
    }
 

    @Then("^template should not be null$")
    public void template_should_not_be_null() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        if (template == null) {
            throw new Exception("Default Template is null");
        }
    }

    @Then("^template should cotain last name$")
    public void template_should_cotain_last_name() throws Throwable {
        System.out.println(template);
        if (!template.getString("Content").contains("{LastName}")) {
            throw new Exception("Default Template should contaain last name");
        }
    }


}

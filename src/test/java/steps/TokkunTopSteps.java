package steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokkunTopSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^ユーザーは未ログイン$")
    public void ユーザーは未ログイン() {
    }

    @When("^特訓トップにアクセスする$")
    public void 特訓トップにアクセスする() {
        site.visit("tokkun/tokkun_top.jsp");
    }

    @Then("^ログインページに遷移する$")
    public void ログインページに遷移する() {
        assertEquals("Login", driver.getCurrentTitle());
    }
}

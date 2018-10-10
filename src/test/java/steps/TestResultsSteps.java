package steps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class TestResultsSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @When("^User is on the Test Results page$")
    public void userIsOnTheTestResultsPage() {
        site.visit("test_result.jsp");
        driver.pageShouldContain("Test Results");
    }



    @Given("^The user took a test with (\\d+) questions$")
    public void theUserTookATestWithQuestions(int totalQuestions) {
    }


    @And("^He answered (\\d+) questions correctly$")
    public void heAnsweredQuestionsCorrectly(int correctAnswers) {
       }


    @Then("^The Test results page displays \"([^\"]*)\" and \"([^\"]*)\"$")
    public void theTestResultsPageDisplaysAnd(String message, String percentage) {
        driver.expectElementWithIdToContainText("message", message);
        driver.expectElementWithIdToContainText("percentage", percentage);
    }
}

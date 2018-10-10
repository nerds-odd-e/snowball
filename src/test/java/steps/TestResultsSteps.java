package steps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class TestResultsSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @Given("^User is on the Test Results page$")
    public void userIsOnTheTestResultsPage() throws Throwable {
        site.visit("Test_Result.jsp");
    }



    @Given("^The user took a test with (\\d+) questions$")
    public void theUserTookATestWithQuestions(int totalQuestions) throws Throwable {
        driver.setTextField("totalQuestions", String.valueOf(totalQuestions));
    }


    @And("^He answered (\\d+) questions correctly$")
    public void heAnsweredQuestionsCorrectly(int correctAnswers) throws Throwable {
        driver.setTextField("correctAnswers", String.valueOf(correctAnswers));
    }


    @Then("^The Test results page displays \"([^\"]*)\" and \"([^\"]*)\"$")
    public void theTestResultsPageDisplaysAnd(String message, String percentage) throws Throwable {
        driver.expectElementWithIdToContainText("message", message);
        driver.expectElementWithIdToContainText("percentage", percentage);
    }
}

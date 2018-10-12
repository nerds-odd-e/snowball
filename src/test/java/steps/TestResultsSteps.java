package steps;
import com.odde.massivemailer.model.QuestionResponse;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class TestResultsSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();


    @When("^User is on the Test Results page$")
    public void userIsOnTheTestResultsPage() {
        site.visit("testresult?quizId=test-cucumber-001");
        driver.pageShouldContain("Test Results");
    }

    @Then("^The Test results page displays \"([^\"]*)\" and \"([^\"]*)\"$")
    public void theTestResultsPageDisplaysAnd(String message, String percentage) {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        driver.expectElementWithIdToContainText("percentage", percentage);
        System.out.println(message);
        driver.expectElementWithIdToContainText("message", message);

    }

    @Given("^User finished a exam with (\\d+) out of (\\d+) score$")
    public void userFinishedAExamWithOutOfScore(int correctAnswer, int totalNumberOfQuestions) throws Throwable {
        for(int i = 0; i< totalNumberOfQuestions; i++) {
            QuestionResponse qr = QuestionResponse.createIt("test_id", "test-cucumber-001",
                    "question_id", i,
                    "is_answer_correct", i< correctAnswer);
        }
        Assert.assertEquals(10, QuestionResponse.where("test_id = ? ","test-cucumber-001").size());
    }
}

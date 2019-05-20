package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;

public class AnswerTokkunQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private Question questionA;

    @When("^the user started to do tokkun$")
    public void 特訓回答ページに遷移する() {
        site.visit("tokkun/question");
    }

    @Then("^the user should see the end of tokkun page")
    public void 特訓のendofthetestが表示される() {
        assertEquals(driver.getCurrentTitle(), "End Of Test");
    }

}

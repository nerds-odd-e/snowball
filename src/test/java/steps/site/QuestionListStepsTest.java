package steps.site;

import com.odde.massivemailer.factory.QuestionBuilder;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionListStepsTest {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^質問がない$")
    public void ShowQuestionList() {
        site.visit("onlinetest/question_list.jsp");
        assertEquals(driver.getCurrentTitle(), "Question List");
    }

    @Given("^質問を追加した$")
    public void AddQuestion() throws Throwable {
        new QuestionBuilder()
                .aQuestion("test1", "test done?")
                .withWrongOption("食べ物")
                .withWrongOption("飲み物")
                .withWrongOption("国")
                .withWrongOption("動物")
                .withCorrectOption("以上の何でもない")
                .please();

        new QuestionBuilder()
                .aQuestion("test2", "test done?")
                .withWrongOption("食べ物")
                .withWrongOption("飲み物")
                .withWrongOption("国")
                .withWrongOption("動物")
                .withCorrectOption("以上の何でもない")
                .please();
    }

    @When("^質問リストのページをクリックする$")
    public void 質問リストのページをクリックする() throws Throwable {
        site.visit("onlinetest/question_list.jsp");
        assertEquals(driver.getCurrentTitle(), "Question List");
    }


    @Then("^質問リストに追加した質問が表示されている$")
    public void 質問リストに追加した質問が表示されている() throws Throwable {
        assertTrue(driver.getBodyText().contains("test1"));
        assertTrue(driver.getBodyText().contains("test2"));
    }
}

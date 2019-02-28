package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.it.Ma;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TokkunSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private OnlineTest onlineTest;
    private final WebDriverWrapper driver = site.getDriver();

    @When("^ユーザがtokkunページへのリンクをクリックする$")
    public void ユーザがtokkunページへのリンクをクリックする() {
    }

    @Then("^問題が表示されている$")
    public void 問題が表示されている() {
        String actualPageName = driver.findElementById("description").getText();
        assertEquals("dummy",actualPageName);
    }


    @Given("^there (\\d+) question in the system$")
    public void there_question_in_the_system(int arg1) throws Throwable {
        new QuestionBuilder()
                .aQuestion("dummy", null, "scrum")
                .withCorrectOption("CorrectOption")
                .please();
    }

    @When("^\"([^\"]*)\" Ha tokkunを受講しmasu in different days$")
    public void ha_tokkunを受講しmasu_in_different_days(String arg1) throws Throwable {
        onlineTest = new OnlineTest(1);
        LocalDate answeredTime = LocalDate.now();
        onlineTest.recordAnswerWithTime(answeredTime);
    }

    @Then("^He should see the follow numbers of question:$")
    public void he_should_see_the_follow_numbers_of_question(DataTable arg1) throws Throwable {
        Map<String, Integer> map = arg1.asMap(String.class,Integer.class);
        for (String key: map.keySet()) {
            site.visit("onlineTest/tokkun");
            int n = map.get(key).intValue();
        }
    }
}

package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.Question;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.site.MassiveMailerSite;

public class TokkunSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();

    @Given("^(\\d+)日前にOnlineTestを受講した$")
    public void 日前にonlinetestを受講した(int days) {
        new QuestionBuilder()
                .aQuestion("dummy", null, "scrum")
                .withCorrectOption("CorrectOption")
                .please();



        //new AnswerHistoryBuilder()
         //       .aQuestion("001", "")
        //int questionId =

    }

    @When("^ユーザがtokkunページへのリンクをクリックする$")
    public void ユーザがtokkunページへのリンクをクリックする() {

    }

    @Then("^特訓ページに(\\d+)問目が表示されている$")
    public void 特訓ページに_問目が表示されている(int arg1) {

    }

    @Then("^以下の内容が表示されている$")
    public void 以下の内容が表示されている(DataTable arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
        throw new PendingException();
    }

}

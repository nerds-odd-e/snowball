package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.tokkun.QuestionResponseForTokkun;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.time.LocalDateTime;

public class TokkunSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private Question questionA;
    private Question questionB;
    private Question questionC;
    private User sato;

    @Given("^問題Aが追加された$")
    public void 問題aが追加された() {
        questionA = new QuestionBuilder()
                .aQuestion("test", "advice", String.valueOf(Category.SCRUM.getId()))
                .withCorrectOption("correctOption1")
                .withWrongOption("wrongOption1")
                .withWrongOption("wrongOption2")
                .withCorrectOption("correctOption2")
                .please();
    }

    @Given("^佐藤が(\\d+)日前に問題Aを回答した$")
    public void 佐藤が_日前に問題Aを回答した(int arg1) {
        sato = new User("test@kanai.com");
        sato.saveIt();
        LocalDateTime date = LocalDateTime.now().minusDays(arg1);
        QuestionResponseForTokkun.createIt(
                "user_id", sato.getId(), "question_id", questionA.getId(), "counter", 1, "answered_at", date.toString());
    }

    @Given("^問題Bが追加された$")
    public void 問題bが追加された() {
        questionB = new QuestionBuilder()
                .aQuestion("test", "advice", String.valueOf(Category.SCRUM.getId()))
                .withCorrectOption("correctOption1")
                .withWrongOption("wrongOption1")
                .withWrongOption("wrongOption2")
                .withCorrectOption("correctOption2")
                .please();
    }

    @Given("^佐藤が(\\d+)日前に問題Bを回答した$")
    public void 佐藤が_日前に問題Bを回答した(int arg1) {
        LocalDateTime date = LocalDateTime.now().minusDays(arg1);
        QuestionResponseForTokkun.createIt(
                "user_id", sato.getId(), "question_id", questionB.getId(), "counter", 1, "answered_at", date.toString());
    }

    @Given("^問題Cが追加された$")
    public void 問題cが追加された() {
        questionC = new QuestionBuilder()
                .aQuestion("test", "advice", String.valueOf(Category.SCRUM.getId()))
                .withCorrectOption("correctOption1")
                .withWrongOption("wrongOption1")
                .withWrongOption("wrongOption2")
                .withCorrectOption("correctOption2")
                .please();
    }

    @Given("^佐藤は問題Cを回答していない$")
    public void 佐藤は問題cを回答していない() {
        QuestionResponseForTokkun.delete("user_id = ? and question_id = ?", sato.getId(), questionC.getId());
    }

    @Given("^特訓画面が表示されている$")
    public void 特訓画面が表示されている() {
        site.visit("tokkun_top.jsp");
    }

    @Given("^佐藤がログインしている$")
    public void 佐藤がログインしている() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^特訓ボタンを押す$")
    public void 特訓ボタンを押す() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^特訓回答画面に遷移する$")
    public void 特訓回答画面に遷移する() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^佐藤が\"([^\"]*)\"に回答した質問が佐藤に\"([^\"]*)\"$")
    public void 佐藤が_に回答した質問が佐藤に(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}

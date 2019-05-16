package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.User;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.tokkun.QuestionResponseForTokkun;
import com.odde.massivemailer.model.tokkun.Tokkun;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class WrongQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^ユーザが登録されている$")
    public void ユーザが登録されている() throws Throwable {
        User.createIt("id",1, "email","mizukami@gmail.com", "hashed_password", "0", "token", "0", "name", "mizukami", "is_admin", 0);
    }

    @Given("^スクラムとは何ですか？の問題が不正解になっている$")
    public void スクラムとは何ですか_の問題が不正解になっている() {
        LocalDateTime date = LocalDateTime.of(2018, 6, 20,0,0,0);
        QuestionResponseForTokkun.createIt("answered_at", date.toString(),"counter",0 , "question_id", Question.findAll().get(0).getId(), "user_id", User.findAll().get(0).getId());
    }

    @When("^問題に正解する$")
    public void 問題に正解する() {
    }

    @When("^問題に不正解する$")
    public void 問題に不正解する() {
    }

    @Then("^問題が非表示される$")
    public void 問題が非表示される() {
    }

    @Then("^問題が表示される$")
    public void 問題が表示される() {
        site.visit("tokkun/question");
        Assert.assertEquals(driver.findElementById("title").getText(), "Question");
        Assert.assertEquals(driver.findElementById("question").getText(), Question.findAll().get(0).getString("description"));

        // Tokkun tokkun = new Tokkun();
    }

    @When("^(\\d+)時間が経過する$")
    public void 時間が経過する(int arg1) {
    }
}

package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.Category;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokkunSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^特訓画面が表示されている$")
    public void 特訓画面が表示されている() throws Throwable {
        site.visit("tokkun/tokkun_top.jsp");
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }

    @Given("^質問が(\\d+)つある$")
    public void 質問が_つある(int n) throws Throwable {
        for (int i = 0; i < n; i++)
            new QuestionBuilder()
                    .aQuestion(Category.SCRUM)
                    .withWrongOption("wrongOption")
                    .withCorrectOption("correctOption")
                    .please();
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }

    @When("^特訓ボタンを押す$")
    public void 特訓ボタンを押す() throws Throwable {
        driver.clickButton("start_button");
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();


    }


    @Then("^特訓回答画面に遷移する$")
    public void 特訓回答画面に遷移する() throws Throwable {
        assertEquals(driver.getCurrentUrl(), "");
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }


    @Given("^\"([^\"]*)\" is newer than \"([^\"]*)\" \\+ (\\d+) days$")
    public void is_newer_than_days(String currentDate, String lastAnsweredDate, int arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        throw new PendingException();
    }

    @Given("^\"([^\"]*)\"でログインしている$")
    public void でログインしている(String arg0) throws Throwable {
        // TODO: ログイン機能が追加されたら実装する。
    }

    @And("^\"([^\"]*)\"が(\\d+)日以上前に回答した質問が(\\d+)つある$")
    public void が日以上前に回答した質問がつある(String arg0, int arg1, int n) throws Throwable {

        // TODO:ユーザの回答履歴テーブルができれば追加する。

    }

    @And("^\"([^\"]*)\"が(\\d+)日以上前に回答した質問が表示されている$")
    public void が日以上前に回答した質問が表示されている(String arg0, int arg1) throws Throwable {
        assertEquals(driver.findElementById("question"),"myTest");

    }
}

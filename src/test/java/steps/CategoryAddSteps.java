package steps;

import com.odde.massivemailer.model.User;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.mock.web.MockHttpServletRequest;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;

public class CategoryAddSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private User user;
    private MockHttpServletRequest request = new MockHttpServletRequest();

    @Given("^Adminがログインする$")
    public void adminがログインする() {
        site.visit("");
        User user = User.createIt("id", 1, "email", "mizukami@gmail.com", "hashed_password", "0", "token", "0", "name", "mizukami", "is_admin", 1);
        request.getSession().setAttribute("user_id", user.getLongId());
    }

    @When("^カテゴリ追加ボタンを押す")
    public void カテゴリ追加ボタンを押す() throws Throwable {
        driver.clickButton("add_category_button");
    }

    @Then("^カテゴリ追加画面に遷移する$")
    public void カテゴリ追加画面に遷移する() throws Throwable {
        assertEquals("Add Category", driver.getCurrentTitle());
    }

    @When("^新しいカテゴリを入力する$")
    public void 新しいカテゴリを入力する() throws Throwable {
        driver.setTextField("category_name","新カテゴリ");
    }

    @When("^決定ボタンをクリックする$")
    public void 決定ボタンをクリックする() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }

    @Then("^問題作成画面に遷移する$")
    public void 問題作成画面に遷移する() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^新しいカテゴリが選択できる$")
    public void 新しいカテゴリが選択できる() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

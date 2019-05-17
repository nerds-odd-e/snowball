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
import static org.junit.Assert.assertTrue;

public class CategoryAddSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private User user;
    private MockHttpServletRequest request = new MockHttpServletRequest();

    @Given("^メニューを選択できる画面に遷移する$")
    public void メニューを選択できる画面に遷移する() {
        site.visit("");
    }

    @Given("^ユーザーはAdminである$")
    public void ユーザーはadminである() {
        user = User.createIt("id", 1, "email", "mizukami@gmail.com", "hashed_password", "0", "token", "0", "name", "mizukami", "is_admin", 1);
    }

    @When("^ユーザーがログインする$")
    public void ユーザーがログインする() {
        request.getSession().setAttribute("user_id", user.getLongId());
    }

    @Then("^カテゴリ追加ボタンが見える$")
    public void カテゴリ追加ボタンが見える() {
        assertTrue(driver.findElementById("add_category_button").getText().contains("Add Category"));
    }


    @When("^カテゴリ追加ボタンを押す")
    public void カテゴリ追加ボタンを押す() {
        driver.clickButton("add_category_button");
    }

    @Then("^カテゴリ追加画面に遷移する$")
    public void カテゴリ追加画面に遷移する() {
        assertEquals("Add Category", driver.getCurrentTitle());
    }

    @When("^新しいカテゴリを入力する$")
    public void 新しいカテゴリを入力する() {
        driver.setTextField("category_name","新カテゴリ");
    }

    @When("^決定ボタンをクリックする$")
    public void 決定ボタンをクリックする() {
        driver.clickButton("add_category");
    }

    @Then("^問題作成画面に遷移する$")
    public void 問題作成画面に遷移する() {
        assertEquals("Add Question", driver.getCurrentTitle());
    }

    @Then("^新しいカテゴリが選択できる$")
    public void 新しいカテゴリが選択できる() {
        assertTrue(driver.findElementById("categoryList").getText().contains("新カテゴリ"));
    }


}

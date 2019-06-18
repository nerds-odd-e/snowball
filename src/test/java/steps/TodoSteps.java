package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.site.MassiveMailerSite;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();

    @Given("^テキストボックスに\"([^\"]*)\"が入力されている$")
    public void テキストボックスに_が入力されている(String text) throws Throwable {
        site.visit("/index.html");
        site.getDriver().setTextField("todo_item", text);
    }

    @When("^登録ボタンをおす$")
    public void 登録ボタンをおす() throws Throwable {
        site.getDriver().clickButton("todo_register");
    }

    @Then("^\"([^\"]*)\"がリストにが表示されている$")
    public void がリストにが表示されている(String arg1) throws Throwable {
        List<WebElement> todo_list = site.getDriver().findElements(By.className("todo_list"));
        assertEquals(todo_list.size(), 1);
        assertThat(todo_list.get(0).findElement(By.tagName("input"))).isNotNull();
        assertThat(todo_list.get(0).findElement(By.tagName("input")).getAttribute("value")).isEqualTo(arg1);
    }

    @When("^リロードをする$")
    public void リロードをする() throws Throwable {
        site.visit("/index.html");
    }
}

package steps;

import com.odde.massivemailer.model.Todo;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.After;
import steps.driver.DBConnectionFactory;
import steps.driver.WebDriverFactory;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;


public class TodoSteps {

    @Before
    public void beforeScenario() {
        DBConnectionFactory.prepare();
    }

    @After
    public void afterScenario() {
        DBConnectionFactory.close();
        WebDriverFactory.resetAll();
    }

    private final MassiveMailerSite site = new MassiveMailerSite();

    @Given("^Todo一覧ページに遷移する$")
    public void todo一覧ページに遷移する() {
        site.visit("/todos.jsp");
    }

    @Then("^Todo一覧ページが表示される$")
    public void todo一覧ページが表示される() {
        assertEquals(true, site.getDriver().getBodyText().contains("Todos List"));
    }


    @Given("^Todoが(\\d+)つある$")
    public void todoが_つある(int numberOfTodo) {
        Todo.createIt("title", "craft beer", "status", "new");
        Todo.createIt("title", "sake", "status", "new");
    }

    @Then("^Todoが複数表示されている$")
    public void todoが複数表示されている() throws InterruptedException {
        Thread.sleep(1000);
        String bodyText = site.getDriver().getBodyText();
        assertEquals(bodyText, true, bodyText.contains("sake"));
        assertEquals(bodyText, true, bodyText.contains("beer"));
    }
}

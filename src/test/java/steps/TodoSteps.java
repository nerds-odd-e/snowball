package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;


public class TodoSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();

    @Given("^Todo一覧ページに遷移する$")
    public void todo一覧ページに遷移する() {
        site.visit("/todos");
    }

    @Then("^Todo一覧ページが表示される$")
    public void todo一覧ページが表示される() {
        assertEquals(true, site.getDriver().getBodyText().contains("hello"));
    }


    @Given("^Todoが(\\d+)つある$")
    public void todoが_つある(int numberOfTodo) throws Throwable {
//        Todo.createIt();
        throw new PendingException();
    }

    @Then("^Todoが複数表示されている$")
    public void todoが複数表示されている() throws Throwable {
        throw new PendingException();
    }
}

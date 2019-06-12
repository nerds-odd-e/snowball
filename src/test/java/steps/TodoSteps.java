package steps;

import com.odde.massivemailer.model.Todo;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.site.MassiveMailerSite;


public class TodoSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();

    @Given("^Todo一覧ページに遷移する$")
    public void todo一覧ページに遷移する() {
        site.visit("/");
        site.visit("/index.html");
    }

    @Then("^Todo一覧ページが表示される$")
    public void todo一覧ページが表示される() {
        site.getDriver().pageShouldContain("Todos List");
    }


    @Given("^Todoが(\\d+)つある$")
    public void todoが_つある(int numberOfTodo) {
        Todo.createIt("title", "craft beer", "status", "new");
        Todo.createIt("title", "sake", "status", "new");
    }

    @Then("^Todoが複数表示されている$")
    public void todoが複数表示されている() {
        site.getDriver().pageShouldContain("sake");
        site.getDriver().pageShouldContain("beer");
    }

    @When("^\"([^\"]*)\"を\"([^\"]*)\"に入力$")
    public void をに入力(String value, String name) {
        site.getDriver().setTextField(name, value);
    }


    @And("^\"([^\"]*)\"が表示されている$")
    public void が表示されている(String text) {
        site.getDriver().pageShouldContain(text);
    }

    @And("^\"([^\"]*)\"をクリック$")
    public void をクリック(String id) {
        site.getDriver().clickButton(id);
    }

    @Given("^以下のTodoがある$")
    public void 以下のtodoがある(DataTable dataTable) {
        dataTable.asList(String.class).forEach(title -> {
            Todo.createIt("title", title, "status", "todo");
        });
    }

    @And("^\"([^\"]*)\"が表示されていない$")
    public void が表示されていない(String text) {
        site.getDriver().pageShouldNotContain(text);
    }
}

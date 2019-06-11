package steps.site;

import com.odde.snowball.model.SentMail;
import org.junit.Assert;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import steps.site.pages.*;

import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class SnowballSite {
    public final WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public String baseUrl() {
        return "http://localhost:8060/";
    }

    public WebDriverWrapper getDriver() {
        return driver;
    }

    public void visit(String path) {
        driver.visit(baseUrl() + path);
    }

    public AddContactPage addContactPage() {
        return new AddContactPage(this);
    }

    public EnrollParticipantPage enrollParticipantPage() {
        return new EnrollParticipantPage(this);
    }

    public CourseListPage courseListPage() {
        return new CourseListPage(this);
    }

    public ImagePage imagePage() {
        return new ImagePage(this);
    }

    public InitializePasswordPage initializePasswordPage() {
        return new InitializePasswordPage(this);
    }

    private String allEmailsThatGotMessages() {
        return repo(SentMail.class).findAll().stream().map(m ->
                String.format("\t\t%s: %s", m.getReceivers(), m.getSubject())
        ).collect(Collectors.joining("\n"));
    }

    public void ExpectNoEmailTo(String email) {
        String all = allEmailsThatGotMessages();
        if (!all.contains(email)) {
            Assert.fail(String.format("This email should have received message: %s\n\tFound message to:\n%s", email, all));
        }
    }

    public void ExpectEmailTo(String email) {
        String all = allEmailsThatGotMessages();
        if (all.contains(email)) {
            Assert.fail(String.format("This email should not receive any message: %s\n\tFound message to:\n%s", email, all));
        }
    }

}

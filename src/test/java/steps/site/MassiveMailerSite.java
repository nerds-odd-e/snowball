package steps.site;

import com.odde.massivemailer.model.SentMail;
import org.javalite.activejdbc.Model;
import org.junit.Assert;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import steps.site.pages.*;

import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class MassiveMailerSite {
    public WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public String baseUrl() {
        return "http://localhost:8060/massive_mailer/";
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

    public AddContactBatchPage addContactBatchPage(){
        return new AddContactBatchPage(this);
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
        return SentMail.findAll().stream().map(m ->
                String.format("\t\t%s: %s", m.get("receivers").toString(), m.get("subject").toString())
        ).collect(Collectors.joining("\n"));
    }

    public void ExpectNoEmailTo(String email) {
        String all = allEmailsThatGotMessages();
        if (!all.contains(email)) {
            BiConsumer<StringBuilder, Model> a = (result, m)-> result.append(m.get("receivers").toString());
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

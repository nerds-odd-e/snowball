package steps.page;

import com.google.common.collect.Multiset;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class MassiveMailerSite {
    public WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public String baseUrl() {
        return "http://localhost:8070/massive_mailer/";
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
}

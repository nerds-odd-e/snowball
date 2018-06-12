package steps.site;

import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import steps.site.pages.AddContactPage;
import steps.site.pages.CourseListPage;
import steps.site.pages.EnrollParticipantPage;
import steps.site.pages.ImagePage;

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

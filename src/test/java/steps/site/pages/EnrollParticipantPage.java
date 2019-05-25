package steps.site.pages;

import com.odde.massivemailer.model.Course;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class EnrollParticipantPage {
    private final MassiveMailerSite site;
    private final WebDriverWrapper driver;

    public EnrollParticipantPage(MassiveMailerSite site) {
        this.site = site;
        this.driver = site.driver;
    }

    public void enrollParticipants(String courseName, String participants) {
        Course course = Course.getCourseByName(courseName);
        driver.visit(site.baseUrl() + "course_detail.jsp?id=" + course.getStringId());
        driver.executeJavaScript("document.getElementById('participants').value = '" + participants + "';");
        driver.clickButton("add_button");
    }

}

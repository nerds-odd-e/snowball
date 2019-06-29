package cucumber.steps.site.pages;

import com.odde.snowball.model.Course;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class EnrollParticipantPage {
    private final SnowballSite site;
    private final WebDriverWrapper driver;

    public EnrollParticipantPage(SnowballSite site) {
        this.site = site;
        this.driver = site.driver;
    }

    public void enrollParticipants(String courseName, String participants) {
        Course course = Course.getCourseByName(courseName);
        driver.visit(site.baseUrl() + "course_detail.jsp?id=" + course.getStringId());
        driver.executeJavaScript("document.getElementById('participants').value = '" + participants + "';");
        driver.click("#add_button");
    }

}

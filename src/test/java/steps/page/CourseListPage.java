package steps.page;

import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class CourseListPage {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/course_list.jsp";
    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public void sendPreviewEmailFor(String a_course) {
        driver.visit(BASE_URL);
        driver.clickButtonByName("preview " + a_course);
    }

    public void sendPrecourseEmailFor(String aCourse) {
        driver.visit(BASE_URL);
        driver.clickButtonByName("precourse " + aCourse);
    }
}

package steps.page;

import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import java.sql.SQLException;

public class EnrollParticipantPage {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/enrollParticipant.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    public void addStudentTo(String a_course, String email) {
        driver.visit(BASE_URL);
        driver.setDropdownByText("courseId", a_course);
        driver.setTextField("email", email);
        driver.clickButton("add_button");
    }
}

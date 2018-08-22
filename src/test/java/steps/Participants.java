package steps;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;


public class Participants {
    @When("^the course \"([^\"]*)\" is selected$")
    public void the_course_is_selected(String courseName) throws Throwable {
        Course course = Course.getCourseByName(courseName);
        List<Participant> partcipants = Participant.whereHasCourseId(course.getLongId());
        List<ContactPerson> participantDetails = new ArrayList<ContactPerson>();
        for (Participant partcipant:partcipants) {
            participantDetails.add(ContactPerson.findById(partcipant.getContactPersonId()));
        }
        throw new PendingException();
    }

    @Then("^course participant should contain $")
    public void course_participant_should_contain(String emailId) throws Throwable {

    }
}

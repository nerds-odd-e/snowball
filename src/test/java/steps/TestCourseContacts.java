package steps;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.ArrayList;
import java.util.List;



public class TestCourseContacts {
    List<ContactPerson> participantDetails = new ArrayList<ContactPerson>();
    @When("^user select course name \"([^\"]*)\"$")
    public void user_select_course_name(String courseName) throws Throwable {
        //Course course = Course.getCourseByName(courseName);
        //List<Participant> partcipants = Participant.whereHasCourseId(String.valueOf(course.getId()));

        //for (Participant partcipant:partcipants) {
        //    participantDetails.add(ContactPerson.findById(partcipant.getContactPersonId()));
        //}
    }

    @Then("^Course should return contact with \"([^\"]*)\"$")
    public void course_should_return_contact_with(String emailId) throws Throwable {
        //boolean emailFound = false;
        //for (ContactPerson contact: participantDetails) {
        //    if (contact.getEmail().equals(emailId)) {
        //        emailFound=true;
        //    }
        //}
        //if (!emailFound) {
        //    throw new Exception("Email not found");
        //}
    }


}
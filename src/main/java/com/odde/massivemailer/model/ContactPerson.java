package com.odde.massivemailer.model;

import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.ValidationException;
import com.odde.massivemailer.service.LocationProviderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;
import java.util.stream.Collectors;

import static com.odde.massivemailer.model.base.Repository.repo;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContactPerson extends Entity<ContactPerson> {
    private String firstName;
    private String lastName;
    @NotNull(message = "Eamil cannot be null")
    @Pattern(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Email should be valid")
    private String email;
    private String company = "";
    private String city;
    private String country;
    private String courseList;
    private String sentDate;
    @Valid
    private Location geoLocation;

    private static int EMAIL_INDEX = 0;
    private static int FIRSTNAME_INDEX = 1;
    private static int LASTNAME_INDEX = 2;
    private static int COMPANY_INDEX = 3;
    private static int COUNTRY_INDEX = 4;
    private static int CITY_INDEX = 5;

    public static void createContactsFromCSVData(String csvData) {

        List<ContactPerson> contacts = prepareContactsList(csvData);

        for (ContactPerson contact1 : contacts) {
            ContactPerson contact;
            contact = contact1;
            contact.save();
        }
    }

    static List<ContactPerson> prepareContactsList(String csvData) {

        String[] contactPersonList = csvData.split(";");
        List<ContactPerson> contacts = new ArrayList<>();

        for (int i = 1; i < contactPersonList.length; i++) {
            String currentContact = contactPersonList[i];
            String[] contactInformation = currentContact.split(",");

            Map<String, String> map = new HashMap<>();
            map.put("firstName", contactInformation[FIRSTNAME_INDEX]);
            map.put("email", contactInformation[EMAIL_INDEX]);
            map.put("lastName", contactInformation[LASTNAME_INDEX]);
            map.put("company", contactInformation[COMPANY_INDEX]);
            map.put("city", contactInformation[CITY_INDEX]);
            map.put("country", contactInformation[COUNTRY_INDEX]);
            ContactPerson contactPerson = repo(ContactPerson.class).fromMap(map);
            contacts.add(contactPerson);
        }

        return contacts;
    }

    public static List<ContactPerson> getContactListFromCompany(String company) {
        return repo(ContactPerson.class).findBy("company", company);
    }

    public static ContactPerson getContactByEmail(String emailAddress) {
        return repo(ContactPerson.class).findFirstBy("email", emailAddress);
    }

    public String location() {
        return LocationProviderService.locationString(city, country);
    }

    public void AddToCourse(ObjectId courseId) {
        Participant contactParticipant = new Participant(getId(), courseId);
        contactParticipant.save();
    }

    @Override
    public void onBeforeSave() {
        if (city == null || city.isEmpty())
            return;
        geoLocation = new LocationProviderService().getCoordinate(city, country);
        ContactPerson person = repo(ContactPerson.class).findFirstBy("email", this.email);
        if (person != null && !person.id.equals(id)) {
            throw new ValidationException("email", "should be unique");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPerson entity = (ContactPerson) o;
        return Objects.equals(email, entity.email);
    }

    public List<Participant> participants() {
        return repo(Participant.class).findBy("contactPersonId", getId());
    }

    public List<Course> courseParticipation() {
        List<Participant> participants = participants();
        return participants.stream().map(Participant::course).collect(Collectors.toList());
    }

}

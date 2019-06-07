package com.odde.massivemailer.model;

import com.google.common.base.Strings;
import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
import com.odde.massivemailer.service.LocationProviderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.javalite.activejdbc.Errors;

import java.util.*;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContactPerson extends Entity {
    private String firstName;
    private String lastName;
    private String email;
    private String company = "";
    private String city;
    private String country;
    private String courseList;
    private String sentDate;
    private Double longitude;
    private Double latitude;

    private static int EMAIL_INDEX = 0;
    private static int FIRSTNAME_INDEX = 1;
    private static int LASTNAME_INDEX = 2;
    private static int COMPANY_INDEX = 3;
    private static int COUNTRY_INDEX = 4;
    private static int CITY_INDEX = 5;

//    static {
//        validatePresenceOf("email");
//        validateEmailOf("email").message("email is invalid");
//        validateWith(new UniquenessValidator("email"));
//    }

    public static Repository<ContactPerson> repository() {
        return new Repository<>(ContactPerson.class, "contacts");
    }

    public static void createContactsFromCSVData(String csvData) {

        List<ContactPerson> contacts = prepareContactsList(csvData);

        for (ContactPerson contact1 : contacts) {
            ContactPerson contact;
            contact = contact1;
            contact.saveIt();
        }
    }

    public static List<ContactPerson> prepareContactsList(String csvData) {

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
            ContactPerson contactPerson = ContactPerson.repository().fromMap(map);
            contacts.add(contactPerson);
        }

        return contacts;
    }

    public static List<ContactPerson> getContactListFromCompany(String company) {
        return repository().findBy("company", company);
    }

    public static ContactPerson getContactByEmail(String emailAddress) {
        return repository().findFirstBy("email", emailAddress);
    }

    public String location() {
        return LocationProviderService.locationString(city, country);
    }

    public Location geoCoordinates() {
        return new Location(location(), latitude, longitude);
    }

    public void AddToCourse(ObjectId courseId) {
        Participant contactParticipant = new Participant(getId(), courseId);
        contactParticipant.saveIt();
    }

    public ContactPerson saveIt() {
        repository().save(this);
        return this;
    }

    @Override
    public boolean onBeforeSave() {
        if (Strings.isNullOrEmpty(city))
            return true;
        Location coordinate = new LocationProviderService().getCoordinate(city, country);
        if (coordinate.getLat() == null) {
            // TODO: throw new ValidationException("city cannot be located");
            return false;
        }
        ContactPerson person = repository().findFirstBy("email", this.email);
        if (person != null && !person.id.equals(id)) {
            // TODO: throw new ValidationException("city cannot be located");
            return false;
        }
        setLatitude(coordinate.getLat());
        setLongitude(coordinate.getLng());
        return true;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPerson entity = (ContactPerson) o;
        return Objects.equals(email, entity.email);
    }

    public List<Participant> getParticipants() {
        return Participant.repository().findBy("contactPersonId", getId());
    }

    public List<Course> getCourseParticipation() {
        List<Participant> participants = getParticipants();
        return participants.stream(). map(Participant::getCourse). collect(Collectors.toList());
    }

    public boolean save() {
        saveIt();
        return getId() != null;
    }

    public Errors errors() {
        Errors errors = new Errors();
        errors.put("email", "should be unique");
        return errors;

    }

}

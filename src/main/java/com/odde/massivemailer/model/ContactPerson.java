package com.odde.massivemailer.model;

import com.odde.massivemailer.model.callback.LocationCallbacks;
import com.odde.massivemailer.model.validator.LocationValidator;
import com.odde.massivemailer.model.validator.UniquenessValidator;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;
import java.util.stream.Collectors;


@Table("contact_people")
public class ContactPerson extends ApplicationModel {
    public static final String FIRSTNAME = "FirstName";
    public static final String LASTNAME = "LastName";
    public static final String EMAIL = "Email";
    public static final String COMPANY = "Company";
    public static final String LOCATION = "Location";
    public static final String LONGITUDE = "Longitude";
    public static final String LATITUDE = "Latitude";
    private static final int EMAIL_INDEX = 0;
    private static final int FIRSTNAME_INDEX = 1;
    private static final int LASTNAME_INDEX = 2;
    private static final int COMPANY_INDEX = 3;
    private static final int COUNTRY_INDEX = 4;
    private static final int CITY_INDEX = 5;

    static {
        validatePresenceOf("email");
        validateEmailOf("email").message("email is invalid");
        validateWith(new UniquenessValidator("email"));
        validateWith(new LocationValidator("city"));
        callbackWith(new LocationCallbacks());
    }

    public Map<String, String> attributes = new HashMap<>();

    public static void createContactsFromCSVData(String csvData) {

        List<ContactPerson> contacts = prepareContactsList(csvData);

        for (int i = 0; i < contacts.size(); i++) {
            ContactPerson contact;
            contact = contacts.get(i);
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
            map.put("firstname", contactInformation[FIRSTNAME_INDEX]);
            map.put("email", contactInformation[EMAIL_INDEX]);
            map.put("lastname", contactInformation[LASTNAME_INDEX]);
            map.put("company", contactInformation[COMPANY_INDEX]);
            map.put("city", contactInformation[CITY_INDEX]);
            map.put("country", contactInformation[COUNTRY_INDEX]);
            ContactPerson contactPerson = new ContactPerson().fromMap(map);
            contacts.add(contactPerson);
        }

        return contacts;
    }

    public static List<ContactPerson> getContactListFromCompany(String company) {
        return where("company = ?", company);
    }

    public static ContactPerson getContactByEmail(String emailAddress) {
        LazyList<ContactPerson> list = where("email = ?", emailAddress);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public static ContactPerson getContactById(Integer contactId) {
        LazyList<ContactPerson> list = where("id = ?", contactId.intValue());
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public Double getLatitude() {
        return getDoubleAttribute(LATITUDE);
    }

    public Double getLongitude() {
        return getDoubleAttribute(LONGITUDE);
    }

    public String getName() {
        return getAttribute(FIRSTNAME);
    }

    public void setName(String name) {
        setAttribute(FIRSTNAME, name);
    }

    public String getEmail() {
        return getAttribute(EMAIL);
    }

    public void setEmail(String email) {
        setAttribute(EMAIL, email);
    }

    public String getLastname() {
        return getAttribute(LASTNAME);
    }

    public void setLastname(String lastname) {
        setAttribute(LASTNAME, lastname);
    }

    public String getCompany() {
        return getAttribute(COMPANY);
    }

    public void setCompany(String company) {
        setAttribute(COMPANY, company);
    }

    private void setAttribute(String name, String value) {
        attributes.put(name, value);
        set(name.toLowerCase(), value);
    }

    public double getDoubleAttribute(String name) {
        return (double) get(name);
    }

    public String getAttribute(String name) {
        Object o = get(name);
        if (o != null)
            return o.toString();
        return "";
    }

    public Set<String> getAttributeKeys() {
        return getMetaModel().getAttributeNamesSkipId();
    }

    public String getLocation() {
        return getAttribute(LOCATION);
    }

    public void setLocation(String location) {
        setAttribute(LOCATION, location);
    }

    public Location getGeoCoordinates() {
        return new Location(getLocation(), getDoubleAttribute(LATITUDE), getDoubleAttribute(LONGITUDE));
    }

    public boolean AddToCourse(String courseId) {
        int participantId = (int) getId();

        Participant contactParticipant = new Participant(new Integer(participantId), new Integer(courseId));

        return contactParticipant.saveIt();
    }

    public void setCourseList(String coursesList) {
        set("courses_sent", coursesList);
    }

    public Object getCoursesList() {
        return get("courses_sent");
    }

    public Object getSentDate() {
        return get("date_sent");
    }

    public void setSentDate(String sentDate) {
        set("date_sent", sentDate);
    }

    @Override
    public boolean equals(Object o) {
        ContactPerson cp = (ContactPerson) o;
        return o != null && Objects.equals(getName(), cp.getName())
                && Objects.equals(getEmail(), cp.getEmail())
                && Objects.equals(getLastname(), cp.getLastname())
                && Objects.equals(getCompany(), cp.getCompany())
                && Objects.equals(getLocation(), cp.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributes());
    }

    public List<Participant> getParticipants() {
        return Participant.where("contact_person_id = ?", getId());
    }

    public List<Course> getCourseParticipation() {
        List<Participant> participants = getParticipants();
        return participants.stream().
                <Course>map(participant -> Course.findById(participant.getCourseId())).
                collect(Collectors.toList());
    }
}

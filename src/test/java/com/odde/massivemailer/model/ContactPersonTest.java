package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.odde.massivemailer.model.base.Repository.repo;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class ContactPersonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateContactObjectWithoutCompany() {
        repo(ContactPerson.class).fromKeyValuePairs(
                "firstName", "name",
                "email", "email@abc.com",
                "lastName", "lastName").saveIt();

        ContactPerson actual = ContactPerson.getContactByEmail("email@abc.com");

        assertEquals("name", actual.getFirstName());
        assertEquals("email@abc.com", actual.getEmail());
        assertEquals("lastName", actual.getLastName());
        assertEquals("", actual.getCompany());
    }


    @Test
    public void testCreateContactObjectValuesWithLocationAndCoOrdinates() {

        Map<String, String> map = new HashMap<>();
        map.put("firstName", "name");
        map.put("email", "email@abc.com");
        map.put("lastName", "lastName");
        map.put("company", "myCompany");
        map.put("city", "Singapore");
        map.put("country", "Singapore");
        repo(ContactPerson.class).fromMap(map).saveIt();
        ContactPerson actual = ContactPerson.getContactByEmail("email@abc.com");

        assertEquals("name", actual.getFirstName());
        assertEquals("lastName", actual.getLastName());
        assertEquals("myCompany", actual.getCompany());
        assertEquals("Singapore", actual.getCity());
        assertNotNull(actual.getGeoLocation().getLatitude());
        assertNotNull(actual.getGeoLocation().getLongitude());

    }

    @Test
    public void UpdateContactWhenEmailSent() {
        ContactPerson p = repo(ContactPerson.class).fromKeyValuePairs("email", "john@gmail.com");
        p.setCourseList("1,2,3");
        p.setSentDate("2017-11-30");
        p.saveIt();

        ContactPerson actual = repo(ContactPerson.class).findById(p.getId());
        assertEquals("1,2,3", actual.getCourseList());
        assertEquals("2017-11-30", actual.getSentDate().toString());
    }

    @Test
    public void test_prepare_list_of_contacts_should_create_listof_contacts() {

        String csvData = preparecsvDataForTest();

        List<ContactPerson> contacts = ContactPerson.prepareContactsList(csvData);
        assertEquals(2, contacts.size());
    }

    private String preparecsvDataForTest() {
        return "email,firstName,lastName,company,country,city;balakg@gmail.com,Bala,GovindRaj,CS,Singapore,Singapore;forshailesh@gmail.com,Shailesh,Thakur,CS,Singapore,Singapore";
    }

    private List<ContactPerson> createContactsForTest() {
        String csvData = preparecsvDataForTest();
        ContactPerson.createContactsFromCSVData(csvData);

        return ContactPerson.prepareContactsList(csvData);
    }

    @Test
    public void noAdditionalContactAddedWhenContactAlreadyExist() {
        List<ContactPerson> contacts = createContactsForTest();
        assertEquals(2, contacts.size());

        int shaileshCount = 0;
        for (ContactPerson cp : contacts) {
            if (cp.getEmail().equals("forshailesh@gmail.com")) {
                shaileshCount++;
            }
        }
        assertEquals(1, shaileshCount);

    }


}

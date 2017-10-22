package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.service.LocationProviderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class EventTest {

    private final Course singaporeEvent = new Course("Scrum In Singapore", "", "Singapore");
    private final Course bangkokEvent = new Course("Code Smells In Bangkok", "", "Bangkok");

    private final ContactPerson singaporeContact = new ContactPerson("testName1", "test1@gmail.com", "test1LastName", "", "Singapore");

    @Test
    public void shouldCreateEventWithTitleAsTest() {
        Event event = new Event("test","content","Singapore");
        Assert.assertEquals("test", event.getTitle());
        Assert.assertEquals("content", event.getContent());
        Assert.assertEquals("Singapore", event.getLocation());

        event.setTitle("test2");
        Assert.assertEquals("test2", event.getTitle());
    }

    @Test
    public void numberOfEventsSendToAllContacts() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        singaporeContact.saveIt();

        assertEquals(2, Course.numberOfEventsNear(ContactPerson.findValidLocations(), new LocationProviderService()));


    }
}

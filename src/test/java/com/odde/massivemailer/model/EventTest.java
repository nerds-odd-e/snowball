package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TestWithDB.class)
public class EventTest {

    @Test
    public void shouldCreateEventWithTitleAsTest() {
        Event event = new Event("test","content","Singapore");
        Assert.assertEquals("test", event.getTitle());
        Assert.assertEquals("content", event.getContent());
        Assert.assertEquals("Singapore", event.getLocation());

        event.setTitle("test2");
        Assert.assertEquals("test2", event.getTitle());
    }

}

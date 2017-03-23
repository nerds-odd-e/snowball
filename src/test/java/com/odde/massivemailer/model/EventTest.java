package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TestWithDB.class)
public class EventTest {

    @Test
    public void shouldCreateEventWithTitleAsTest() {
        Event event = new Event("test");
        Assert.assertEquals("test", event.getTitle());

        event.setTitle("test2");
        Assert.assertEquals("test2", event.getTitle());
    }

}

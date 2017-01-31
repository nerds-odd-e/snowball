package com.odde.massivemailer.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by csd on 2017/01/31.
 */
public class EventTest {

    @Test
    public void shouldCreateEventWithTitleAsTest() {
        Event event = new Event("test");
        Assert.assertEquals("test", event.getTitle());

        event.setTitle("test2");
        Assert.assertEquals("test2", event.getTitle());
    }

}

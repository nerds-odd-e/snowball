package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.service.EventService;

import java.util.*;

public class EventServiceImpl implements EventService {
    private SqliteEvent eventDao = new SqliteEvent();

    public boolean addEvent(Event event) {
        if (event.getTitle() == null ||
                event.getTitle().isEmpty())
            throw new IllegalArgumentException("Event title is mandatory");

        return eventDao.addNewEvent(event);
    }

    @Override
    public List<Event> getAll() {
        return eventDao.getAll();
    }
}

package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.exception.EventAlreadyExistsException;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.service.EventService;

import java.util.*;

public class EventServiceImpl implements EventService {
    private Map<Integer, Event> events = new LinkedHashMap<>();

    private SqliteEvent eventDao = new SqliteEvent();

    public boolean addEvent(Event event) {

        if (event.getTitle() == null ||
                event.getTitle().isEmpty())
            throw new IllegalArgumentException("Event title is mandatory");

        boolean status = eventDao.addNewEvent(event);

        return status;
    }

    @Override
    public List<Event> getAll() {

        return eventDao.getAll();
    }
}

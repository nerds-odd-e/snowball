package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EventAlreadyExistsException;
import com.odde.massivemailer.model.Event;

import java.util.List;

public interface EventService {
    boolean addEvent(Event event);

    List<Event> getAll();
}

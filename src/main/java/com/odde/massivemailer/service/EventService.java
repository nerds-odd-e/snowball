package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EventAlreadyExistsException;
import com.odde.massivemailer.model.Event;

import java.util.List;

public interface EventService {
    int addEvent(Event event) throws EventAlreadyExistsException;

    List<Event> getAll();
}

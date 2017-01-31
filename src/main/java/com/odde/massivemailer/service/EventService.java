package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EventAlreadyExistsException;
import com.odde.massivemailer.model.Event;

public interface EventService {
    int addEvent(Event event) throws EventAlreadyExistsException;
}

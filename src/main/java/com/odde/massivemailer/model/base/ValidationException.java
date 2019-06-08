package com.odde.massivemailer.model.base;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationException extends RuntimeException {
    public ValidationException(String s) {
        super(s);
    }

    public <T> ValidationException(Set<ConstraintViolation<Entity<T>>> validate) {

    }
}

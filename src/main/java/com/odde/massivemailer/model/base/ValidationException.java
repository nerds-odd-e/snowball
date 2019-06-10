package com.odde.massivemailer.model.base;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationException extends RuntimeException {
    private Errors errors = new Errors();

    <T> ValidationException(Set<ConstraintViolation<Entity>> validate) {
        validate.forEach(v-> errors.put(v.getPropertyPath().toString(), v.getMessage()));
    }

    public ValidationException(String field, String message) {
        errors.put(field, message);
    }

    public Errors errors() {
        return errors;
    }
}

package com.odde.massivemailer.model.validator;

import com.odde.massivemailer.model.base.Entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.odde.massivemailer.model.base.Repository.repo;

public class LocationValidator implements
        ConstraintValidator<CheckLocation, String> {

    private CheckLocation checkLocation;

    @Override
    public void initialize(CheckLocation checkLocation) {
        this.checkLocation = checkLocation;
    }

    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {

        Entity entity = repo(checkLocation.scope()).findFirstBy(checkLocation.toString(), contactField);
        return true;
    }

}

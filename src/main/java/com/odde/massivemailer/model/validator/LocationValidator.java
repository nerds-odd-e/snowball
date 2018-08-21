package com.odde.massivemailer.model.validator;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.ValidatorAdapter;

public class LocationValidator extends ValidatorAdapter {
    private final String attribute;

    public LocationValidator(String city) {
        this.attribute = city;
        setMessage("cannot be located");
    }

    @Override
    public void validate(Model m) {
        if (m.get("city") == null)
            return;
        if (m.get("latitude") == null) {
           m.addValidator(this, attribute);
        }
    }
}

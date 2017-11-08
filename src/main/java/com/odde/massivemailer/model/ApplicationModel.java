package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

public class ApplicationModel extends Model {
    @Override
    public boolean equals(Object obj) {
        return getLongId().equals(((Model)obj).getLongId());
    }
}

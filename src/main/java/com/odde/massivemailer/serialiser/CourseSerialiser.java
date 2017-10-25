package com.odde.massivemailer.serialiser;

import com.odde.massivemailer.model.Course;

import java.util.Set;

public class CourseSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return Course.attributeNames();
    }
}
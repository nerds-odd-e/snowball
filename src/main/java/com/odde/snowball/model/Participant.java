package com.odde.snowball.model;


import com.odde.snowball.model.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import static com.odde.snowball.model.base.Repository.repo;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Participant extends Entity<Participant> {
    private ObjectId contactPersonId;
    private ObjectId courseId;

    ContactPerson contactPerson() {
        return repo(ContactPerson.class).findById(contactPersonId);
    }

    Course course() {
        return repo(Course.class).findById(courseId);
    }
}
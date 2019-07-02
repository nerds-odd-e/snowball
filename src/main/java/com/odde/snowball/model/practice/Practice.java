package com.odde.snowball.model.practice;

import com.odde.snowball.model.base.Entity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Date;

@Setter
@Getter
public class Practice extends Entity<Practice> {
    private ObjectId userId;
    private int numberOfQuestions;
    private Collection<ObjectId> categories;
    private int[] cycle;

    public Practice(ObjectId userId, int numberOfQuestions, Collection<ObjectId> categories, int[] cycle) {
        this.userId = userId;
        this.numberOfQuestions = numberOfQuestions;
        this.categories = categories;
        this.cycle = cycle;
    }
}

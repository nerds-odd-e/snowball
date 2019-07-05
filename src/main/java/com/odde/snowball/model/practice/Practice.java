package com.odde.snowball.model.practice;

import com.odde.snowball.model.base.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.odde.snowball.model.base.Repository.repo;

@NoArgsConstructor
@Setter
@Getter
public class Practice extends Entity<Practice> {
    private ObjectId userId;
    private int numberOfQuestions;
    private Collection<ObjectId> categories;
    private List<Integer> cycle;

    public Practice(ObjectId userId) {
        this(userId, 1, new ArrayList<>(), Arrays.asList(1, 2, 4));
    }

    public Practice(ObjectId userId, int numberOfQuestions, Collection<ObjectId> categories, List<Integer> cycle) {
        this.userId = userId;
        this.numberOfQuestions = numberOfQuestions;
        this.categories = categories;
        this.cycle = cycle;
    }

    public static Practice fetchPracticeByUserId(ObjectId userId) {
        if (null == userId) {
            return null;
        }
        return repo(Practice.class).findFirstBy("userId", userId);
    }

    public static void generatePractice(ObjectId userId) {
        Practice practice = Practice.fetchPracticeByUserId(userId);

        if (practice == null) {
            practice = new Practice(userId);
            practice.save();
        }
    }

}

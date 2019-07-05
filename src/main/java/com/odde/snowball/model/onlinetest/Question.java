package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.base.Entity;
import com.odde.snowball.model.practice.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.odde.snowball.model.base.Repository.repo;
import static java.util.stream.Collectors.toList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends Entity<Question> {

    @NotNull(message = "Description cannot be empty")
    @NotBlank(message = "Description cannot be empty")
    private String description;
    private String advice;
    @NotNull(message = "Category cannot be empty")
    @NotBlank(message = "Category cannot be empty")
    private ObjectId categoryId;
    private boolean isMultiQuestion;
    private boolean isApproved;

    public void recordQuestionForUser(ObjectId userId, LocalDate date) {
        List<Record> records = repo(Record.class).find(and(eq("userId", userId), eq("questionId", getId())));
        if(records.size() == 0){
            Record record = new Record(userId, getId(), date, 1);
            record.save();
        } else {
            Record record = records.get(0);
            record.setLastUpdated(date);
            record.setCycleState(record.getCycleState()+1);
            record.save();
        }
    }

    boolean isDueForUser(ObjectId userId) {
        List<Integer> cycle = Arrays.asList(1,2,4);
        List<Record> records = repo(Record.class).find(and(eq("userId",userId),eq("questionId",getId())));
        if (records.size()==0){
            return true;
        }
        Record record = records.get(0);
        if (record.getCycleState()==0){
            return true;
        }
        if (record.getCycleState()>cycle.size()){
            return false;
        }
        return !record.getLastUpdated()
                .plusDays(cycle.get(record.getCycleState()-1))
                .isAfter(LocalDate.now());
    }

    public Category category() {
        return repo(Category.class).findById(categoryId);
    }

    public String categoryName() {
        Category category = category();
        if (category == null) {
            return "";
        }
        return category.getName();
    }

    public Collection<QuestionOption> options() {
        return QuestionOption.getForQuestion(this.getId());
    }

    boolean verifyAnswer(List<ObjectId> answeredOptionIds) {
        Collection<QuestionOption> optionsByQuestionId = options();
        List<ObjectId> collectOptions = optionsByQuestionId.stream().filter(QuestionOption::isCorrect).map(Entity::getId).collect(toList());
        return collectOptions.equals(answeredOptionIds);
    }

    public ArrayList<ObjectId> correctOptions() {
        Collection<QuestionOption> optionsByQuestionId = options();
        final ArrayList<ObjectId> correctOptions = new ArrayList<>();
        for (QuestionOption option : optionsByQuestionId) {
            if (option.isCorrect()) {
                correctOptions.add(option.getId());
            }
        }
        return correctOptions;
    }

    public boolean isCorrect(String optionId) {
        return correctOptions().contains(new ObjectId(optionId));
    }

    public void createWrongOption(String optionText) {
        new QuestionOption(optionText, false, getId()).save();
    }

    public void createCorrectOption(String optionText) {
        new QuestionOption(optionText, true, getId()).save();
    }

}

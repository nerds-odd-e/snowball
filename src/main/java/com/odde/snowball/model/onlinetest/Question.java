package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;
import com.odde.snowball.model.practice.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.Valid;
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
    @Valid
    private List<QuestionOption> options = new ArrayList<>();

    public Question(String description, String advice, ObjectId category, boolean multiple, boolean b) {
        this.description = description;
        this.advice = advice;
        this.categoryId = category;
        this.isMultiQuestion = multiple;
        this.isApproved = b;
    }

    public void recordQuestionForUser(User user, LocalDate date) {
        List<Record> records = repo(Record.class).find(and(eq("userId", user.getId()), eq("questionId", getId())));
        if(records.size() == 0){
            Record record = new Record(user.getId(), getId(), date, 1);
            record.save();
        } else {
            Record record = records.get(0);
            record.setLastUpdated(date);
            record.setCycleState(record.getCycleState()+1);
            record.save();
        }
    }

    boolean isDueForUser(User user) {
        List<Integer> cycle = Arrays.asList(1,2,4);
        List<Record> records = repo(Record.class).find(and(eq("userId",user.getId()),eq("questionId",getId())));
        if (records.size()==0){
            return false;
        }
        Record record = records.get(0);
        if (record.getCycleState()==0) {
            return record.getLastUpdated().isBefore(LocalDate.now());
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

    public List<String> correctOptions() {
        return getOptions().stream().filter(QuestionOption::isCorrect).map(QuestionOption::stringId).collect(toList());
    }

    public boolean isCorrect(String optionId) {
        return correctOptions().contains(optionId);
    }

    boolean verifyAnswer(List<String> answeredOptionIds) {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        List<String> collectOptions = optionsByQuestionId.stream().filter(QuestionOption::isCorrect).map(QuestionOption::stringId).collect(toList());
        return collectOptions.equals(answeredOptionIds);
    }

    public void resetCycle(User user, LocalDate date) {
        List<Record> records = repo(Record.class).find(and(eq("userId", user.getId()), eq("questionId", getId())));
        if(records.size() == 0){
            Record record = new Record(user.getId(), getId(), date, 0);
            record.save();
        } else {
            Record record = records.get(0);
            record.setLastUpdated(date);
            record.setCycleState(0);
            record.save();
        }
    }

    public boolean notAnswered(User user) {
        List<Record> records = repo(Record.class).find(and(eq("userId", user.getId()), eq("questionId", getId())));
        return records.size() == 0;
    }

    public Question withOption(String optionDescription, boolean isCorrect) {
        addOption(optionDescription, isCorrect);
        return this;
    }

    public QuestionOption addOption(String optionDescription, boolean isCorrect) {
        QuestionOption questionOption = new QuestionOption(optionDescription, isCorrect);
        options.add(questionOption);
        return questionOption;
    }
}

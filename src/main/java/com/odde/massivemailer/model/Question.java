package com.odde.massivemailer.model;

import com.odde.massivemailer.model.callback.QuestionCallbacks;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;
import java.util.stream.Stream;

@Table("questions")
public class Question extends ApplicationModel {

    private static final String DESCRIPTION = "description";
    private static final String ADVICE = "advice";

    public Map<String, String> attributes = new HashMap<>();

    static {
        validatePresenceOf("description");
        callbackWith(new QuestionCallbacks());
    }

    public static Stream<Object> getAllIds() {
        LazyList<Model> ids = findBySQL("SELECT id FROM questions");
        return ids.stream().map(Model::getId);
    }

    public static Optional<Question> getById(Long questionId) {
        LazyList<Question> question = where("id = ?", questionId);
        return question.stream().findFirst();
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public String getAdvice() {
        return getString(ADVICE);
    }

    public void setDescription(String description) {
        setAttribute(DESCRIPTION, description);
    }

    public void setAdvice(String advice) {
        setAttribute(ADVICE, advice);
    }

    public Collection<AnswerOption> getOptions() {

        return AnswerOption.getForQuestion(this.getLongId());
    }

    private void setAttribute(String name, String value) {
        attributes.put(name, value);
        set(name.toLowerCase(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return Objects.equals(getDescription(), question.getDescription()) && Objects.equals(getAdvice(), question.getAdvice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getAdvice());
    }
}

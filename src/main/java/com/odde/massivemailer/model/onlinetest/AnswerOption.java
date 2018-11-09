package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.ApplicationModel;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;

@Table("options")
public class AnswerOption extends ApplicationModel {

    private static final String DESCRIPTION = "description";
    private static final String IS_CORRECT = "is_correct";
    private static final String QUESTION_ID = "question_id";

    static {
        validatePresenceOf(DESCRIPTION, QUESTION_ID, IS_CORRECT);
    }

    public AnswerOption() {
        //required by framework :(
    }

    private AnswerOption(String description, boolean isCorrect) {
        set(DESCRIPTION, description);
        setIsCorrect(isCorrect);
    }

    public AnswerOption(long questionId, String description, boolean isCorrect) {
        set(QUESTION_ID, questionId);
        set(DESCRIPTION, description);
        setIsCorrect(isCorrect);
    }

    static Collection<AnswerOption> getForQuestion(Long questionId) {
        return where("question_id = ?", questionId);
    }

    static Optional<AnswerOption> getById(Long optionId) {
        LazyList<AnswerOption> answerOption = where("id = ?", optionId);
        return answerOption.stream().findFirst();
    }

    public static AnswerOption create(String description, boolean isCorrect) {
        return new AnswerOption(description, isCorrect);
    }

    public Long getQuestionId() {
        return getLong(QUESTION_ID);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public boolean isCorrect() {
        return getInteger(IS_CORRECT) == 1;
    }

    public void setIsCorrect(boolean isCorrect) {
        set(IS_CORRECT, isCorrect ? 1 : 0);
    }

    void addToQuestion(Long questionLongId) {
        set(QUESTION_ID, questionLongId);
        saveIt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnswerOption)) return false;
        if (!super.equals(o)) return false;
        AnswerOption that = (AnswerOption) o;
        return Objects.equals(getQuestionId(), that.getQuestionId()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(isCorrect(), that.isCorrect());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionId(), getDescription(), isCorrect());
    }
}

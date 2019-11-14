package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserAnswer extends Entity<UserAnswer> {

    private Question question;
    private List<String> selectedOptionIds;
    private User user;
    private String date;

    private String questionId;
    private String userId;

    public UserAnswer() {
    }

    UserAnswer(Question question, List<String> selectedOptionIds, LocalDate date) {
        this.question = question;
        this.selectedOptionIds = selectedOptionIds;
        this.date = getDateString(date);
    }

    UserAnswer(Question question, List<String> selectedOptionIds) {
        this.question = question;
        this.selectedOptionIds = selectedOptionIds;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question){
        this.question = question;
    }

    private List<String> getSelectedOptionIds() {
        return selectedOptionIds;
    }

    public boolean isCorrect() {
        return getQuestion().verifyAnswer(getSelectedOptionIds());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getQuestionId() {
        return null;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getDate() {
        return date;
    }

    public String getDateString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}

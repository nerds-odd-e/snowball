package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class OnlineTest {

    private final List<Question> questions;
    private List<Answer> answers;

    protected OnlineTest(List<Question> questions) {
        this.questions = questions;
        answers = new ArrayList<>();
    }

    public Question getCurrentQuestion() {
        if (!(questions.size() > this.getNumberOfAnsweredQuestions())) {
            return null;
        }
        return questions.get(getNumberOfAnsweredQuestions());
    }

    public int getNumberOfAnsweredQuestions() {
        return this.answers.size();
    }

    public int getNumberOfQuestions() {
        return (questions != null) ? questions.size() : 0;
    }

    public Answer answerCurrentQuestion(List<String> selectedOptionIds, User user, LocalDate date) {
        Question currentQuestion = getCurrentQuestion();
        Answer answer = new Answer(currentQuestion, selectedOptionIds);
        answers.add(answer);
        return answer;
    }

    public TestResult testResult() {
        return new TestResult(answers);
    }

    public abstract String endPageName();

    public String progress(int adjust) {
        return "" + (getNumberOfAnsweredQuestions() + 1 + adjust) + "/" + getNumberOfQuestions();
    }
}

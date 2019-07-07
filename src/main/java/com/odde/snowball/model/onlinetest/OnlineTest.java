package com.odde.snowball.model.onlinetest;

import com.odde.snowball.enumeration.TestType;
import com.odde.snowball.model.User;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.odde.snowball.model.base.Repository.repo;

@Getter
public class OnlineTest {

    private final List<Question> questions;
    private List<Answer> answers;
    private LocalDate answeredTime;

    public OnlineTest(int questionCount) {
        this(new QuestionCollection(repo(Question.class).findAll()).generateQuestionList(repo(Category.class).findAll(), questionCount));
    }

    public OnlineTest(List<Question> questions) {
        this.questions = questions;
        answers = new ArrayList<>();
    }

    public Question getPreviousQuestion() {
        return questions.get(getNumberOfAnsweredQuestions() - 1);
    }

    public Question getCurrentQuestion() {
        return questions.get(getNumberOfAnsweredQuestions());
    }

    public Question getNextQuestion() {
        if (!hasNextQuestion()) {
            throw new NoSuchElementException("OnlineTest not started");
        }
        return questions.get(getNumberOfAnsweredQuestions() + 1);
    }

    public boolean hasNextQuestion() {
        return questions.size() > this.getNumberOfAnsweredQuestions();
    }

    public int getNumberOfAnsweredQuestions() {
        return this.answers.size();
    }

    public long getCorrectAnswerCount() {
        return answers.stream().filter(Answer::isCorrect).count();
    }

    public int getCurrentQuestionIndex() {
        return getNumberOfAnsweredQuestions() + 1;
    }

    public int getNumberOfQuestions() {
        return (questions != null) ? questions.size() : 0;
    }

    public String showFinalMessage() {
        int correctPercentage = generateTestResult().correctPercentage();
        if (correctPercentage < 85) {
            return "基本を学びなおしましょう";
        } else if (correctPercentage == 100) {
            return "あなたはスクラムマスター！";
        }
        return "あともう少し";
    }

    public String getAlertMsg(String lastDoneQuestionId) {
        String alertMsg = "";
        if (!lastDoneQuestionId.equals(String.valueOf(getNumberOfAnsweredQuestions()))) {
            alertMsg = "You answered previous question twice";
        }
        return alertMsg;
    }

    public Answer answerCurrentQuestion(List<String> selectedOptionIds, User user, LocalDate date) {
        Question currentQuestion = getCurrentQuestion();
        Answer answer = new Answer(currentQuestion, selectedOptionIds);
        answers.add(answer);
        if (user != null) {
            if (answer.isCorrect()) {
                currentQuestion.recordQuestionForUser(user, date);
            } else {
                currentQuestion.resetCycle(user, date);
            }
        }
        return answer;
    }

    public void recordAnswerWithTime(LocalDate answeredTime) {
        this.answeredTime = answeredTime;
    }

    public long getPassedDaysSinceAnswered() {
        return Duration.between(this.answeredTime.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
    }

    public TestResult generateTestResult() {
        return new TestResult(questions, answers);
    }

    public TestType getTestType() {
        return null;
    }

    public String getNextPageName() {
        if (hasNextQuestion()) {
            return "/onlinetest/question";
        }
        return "/onlinetest/end_of_test.jsp";
    }
}

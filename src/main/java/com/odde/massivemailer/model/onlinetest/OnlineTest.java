package com.odde.massivemailer.model.onlinetest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class OnlineTest {

    private final List<Question> questions;
    private int numberOfAnsweredQuestions;
    private int correctAnswerCount;

    public OnlineTest(int questionCount) {
        questions = Question.getNRandom(questionCount);
        numberOfAnsweredQuestions = 0;
    }

    public Question getPreviousQuestion() {
        return questions.get(numberOfAnsweredQuestions - 1);
    }

    public Question getCurrentQuestion() {
        return questions.get(numberOfAnsweredQuestions);
    }

    public Question getNextQuestion() {
        if (!hasNextQuestion()) {
            throw new NoSuchElementException("OnlineTest not started");
        }
        Question question = questions.get(numberOfAnsweredQuestions + 1);
        return question;
    }

    public boolean hasNextQuestion() {
        return questions.size() > this.getNumberOfAnsweredQuestions();
    }

    public int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getCurrentQuestionIndex() {
        return this.numberOfAnsweredQuestions + 1;
    }

    public int getNumberOfQuestions() {
        return (questions != null) ? questions.size() : 0;
    }

    public void moveToNextQuestion() {
        numberOfAnsweredQuestions++;
    }

    public int getCorrectPercentage() {
        return (int) ((double) this.correctAnswerCount / getNumberOfQuestions() * 100);
    }

    public String showFinalMessage() {
        if (getCorrectPercentage() < 85) {
            return "基本を学びなおしましょう";
        } else if (getCorrectPercentage() == 100) {
            return "あなたはスクラムマスター！";
        }
        return "あともう少し";
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(int correctlyAnsweredCount) {
        this.correctAnswerCount = correctlyAnsweredCount;
    }

    public void incrementCorrectAnswerCount() {
        correctAnswerCount++;
    }

    public boolean isCorrectAnswer(String answeredOptionId) {
        Question currentQuestion = getCurrentQuestion();
        return currentQuestion.verifyAnswer(answeredOptionId);
    }

    public String getAlertMsg(String lastDoneQuestionId) {
        String alertMsg = "";
        if (!lastDoneQuestionId.equals(String.valueOf(getNumberOfAnsweredQuestions()))) {
            alertMsg = "You answered previous question twice";
        }
        return alertMsg;
    }

    public String getCategoryMessage() {
        if (getCorrectAnswerCount() == questions.size()) {
            return "よくできました";
        }
        String categories = questions.stream()
                .sorted((q1, q2) -> (int) (q1.getLongId() - q2.getLongId()))
                .map(Question::getCategory)
                .distinct()
                .collect(Collectors.joining("と"));
        return categories + "をもっと勉強して";
    }
}

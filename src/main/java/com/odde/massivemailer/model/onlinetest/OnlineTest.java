package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.base.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OnlineTest {

    private final List<Question> questions;
    private int numberOfAnsweredQuestions;
    private int correctAnswerCount;
    private Map<String, Integer> categoryCorrectAnswerCount;
    public List<CategoryTestResult> categoryTestResults;
    public List<Answer> answers;

    public OnlineTest(int questionCount) {
        questions = new QuestionCollection(Question.getAll()).generateQuestionList(Category.repository().findAll(), questionCount);
        numberOfAnsweredQuestions = 0;
        categoryCorrectAnswerCount = new HashMap<>();
        categoryTestResults = new ArrayList<>();
        answers = new ArrayList<>();
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

    public void addAnsweredQuestionNumber() {
        numberOfAnsweredQuestions++;
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

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(int correctlyAnsweredCount) {
        this.correctAnswerCount = correctlyAnsweredCount;
    }

    public void incrementCorrectAnswerCount() {
        correctAnswerCount++;
    }

    private void incrementCategoryQuestionCount(String categoryId) {
        List<CategoryTestResult> collect = categoryTestResults
                .stream()
                .filter(categoryTestResult -> categoryTestResult.categoryId == categoryId)
                .collect(Collectors.toList());
        if (collect.size() < 1) {
            CategoryTestResult categoryTestResult = new CategoryTestResult(categoryId);
            categoryTestResults.add(categoryTestResult);
            collect = categoryTestResults
                    .stream()
                    .filter(result -> result.categoryId == categoryId)
                    .collect(Collectors.toList());
        }
        CategoryTestResult categoryTestResult = collect.get(0);
        categoryTestResult.questionCount++;
    }

    public String getAlertMsg(String lastDoneQuestionId) {
        String alertMsg = "";
        if (!lastDoneQuestionId.equals(String.valueOf(getNumberOfAnsweredQuestions()))) {
            alertMsg = "You answered previous question twice";
        }
        return alertMsg;
    }

    public String getCategoryMessage() {
        if (getShowAdvice()) {
            return "";
        }
        String categories = questions.stream()
                .sorted((q1, q2) -> (int) (q1.getLongId() - q2.getLongId()))
                .map(question -> question.getCategory().getName())
                .distinct()
                .collect(Collectors.joining("と"));

        return categories + "をもっと勉強して";
    }

    public boolean getShowAdvice() {
        return getCorrectAnswerCount() != 0 && (getCorrectAnswerCount() * 1.0 / questions.size() * 1.0) * 100 >= 80;
    }

    public int getCategoryCorrectAnswerCount(String categoryId) {
        Integer count = categoryCorrectAnswerCount.get(categoryId);
        if (count == null) {
            return 0;
        }
        return count;
    }

    public void incrementCategoryCorrectAnswerCount(String categoryId) {
        Integer count = categoryCorrectAnswerCount.get(categoryId);
        if (count == null) {
            count = 0;
        }
        categoryCorrectAnswerCount.put(categoryId, count + 1);
    }

    public boolean answer(String[] answeredOptionIds) {

        String categoryId = getCurrentQuestion().getCategory().getStringId();
        Answer answer = answerCurrentQuestion(Arrays.asList(answeredOptionIds));
        boolean isCorrect = answer.isCorrect();
        if (isCorrect) {
            incrementCorrectAnswerCount();
            incrementCategoryQuestionCount(categoryId);
            incrementCategoryCorrectAnswerCount(categoryId);
            return true;
        }
        return false;
    }

    public List<CategoryTestResult> getFailedCategoryTestResults() {
        return categoryTestResults;
    }

    public Answer answerCurrentQuestion(List<String> selectedOptionIds) {
    Answer answer = new Answer(getCurrentQuestion(), selectedOptionIds);
        answers.add(answer);
        addAnsweredQuestionNumber();
        return answer;
    }
    private LocalDate answeredTime;
    public void recordAnswerWithTime(LocalDate answeredTime) {
        this.answeredTime = answeredTime;

    }

    public long getPassedDaysSinceAnswered() {
        return Duration.between(this.answeredTime.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
    }

    public TestResult generateTestResult() {
        return new TestResult(questions, answers);
    }

}

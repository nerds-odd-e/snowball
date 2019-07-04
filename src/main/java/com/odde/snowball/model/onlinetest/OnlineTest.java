package com.odde.snowball.model.onlinetest;

import com.odde.snowball.enumeration.TestType;
import com.odde.snowball.model.base.Entity;
import com.odde.snowball.model.practice.Record;
import org.bson.types.ObjectId;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

public class OnlineTest {

    private final List<Question> questions;
    private int numberOfAnsweredQuestions;
    private int correctAnswerCount;
    private Map<String, Integer> categoryCorrectAnswerCount;
    public List<CategoryTestResult> categoryTestResults;
    public List<Answer> answers;
    private TestType testType;

    public OnlineTest(int questionCount) {
        this(new QuestionCollection(repo(Question.class).findAll()).generateQuestionList(repo(Category.class).findAll(), questionCount));
        testType = TestType.OnlineTest;
    }

    public OnlineTest(List<Question> questions) {
        this.questions = questions;
        numberOfAnsweredQuestions = 0;
        categoryCorrectAnswerCount = new HashMap<>();
        categoryTestResults = new ArrayList<>();
        answers = new ArrayList<>();
    }

    public static OnlineTest getOnlineTest(ObjectId userId, String category) {
        List<Question> questions = new ArrayList<>();
        questions.add(getQuestionsByRecords(userId));
        OnlineTest onlineTest = new OnlineTest(questions);
        onlineTest.testType = TestType.Practice;
        return onlineTest;
    }

    public static Question getQuestionsByRecords(ObjectId userId) {
        Collection<Record> records = new Record().fetchRecordsByUserId(userId);
        ArrayList<Question> questions = new ArrayList<Question>() {
        };
        if (records.size() == 0) {
            Question question = repo(Question.class).findAll().get(0);
            Record newRecord = new Record(userId, question.getId(), new Date(), 0);
            newRecord.save();
            return question;
        }

        records.stream().filter(record -> record.getCycleState() < 2)
                .forEach(record -> questions.add(repo(Question.class).findById(record.getQuestionId())));

        return questions.isEmpty() ? null : questions.get(0);
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
        return questions.get(numberOfAnsweredQuestions + 1);
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
                .filter(categoryTestResult -> categoryTestResult.categoryId.equals(categoryId))
                .collect(Collectors.toList());
        if (collect.size() < 1) {
            CategoryTestResult categoryTestResult = new CategoryTestResult(categoryId);
            categoryTestResults.add(categoryTestResult);
            collect = categoryTestResults
                    .stream()
                    .filter(result -> result.categoryId.equals(categoryId))
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
                .sorted(Comparator.comparing(Entity::getId))
                .map(question -> question.category().getName())
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

        String categoryId = getCurrentQuestion().category().getStringId();
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

    public TestType getTestType() {
        return testType;
    }
}

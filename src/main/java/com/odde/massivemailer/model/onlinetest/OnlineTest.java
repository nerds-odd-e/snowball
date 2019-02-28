package com.odde.massivemailer.model.onlinetest;

import java.util.*;
import java.util.stream.Collectors;

public class OnlineTest {

    private final List<Question> questions;
    private int numberOfAnsweredQuestions;
    private int correctAnswerCount;
    private Map<Integer, Integer> categoryCorrectAnswerCount;

    public OnlineTest(int questionCount) {
        questions = Question.getNRandom(questionCount);
        numberOfAnsweredQuestions = 0;
        categoryCorrectAnswerCount = new HashMap<>();
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

    public String getAlertMsg(String lastDoneQuestionId) {
        String alertMsg = "";
        if (!lastDoneQuestionId.equals(String.valueOf(getNumberOfAnsweredQuestions()))) {
            alertMsg = "You answered previous question twice";
        }
        return alertMsg;
    }

    public String getCategoryMessage() {
        if ( getShowAdvice() ) {
            return "";
        }
        String categories = questions.stream()
                .sorted((q1, q2) -> (int) (q1.getLongId() - q2.getLongId()))
                .map(question -> Category.getNameById(question.getCategory()))
                .distinct()
                .collect(Collectors.joining("と"));

        return categories + "をもっと勉強して";
    }

    public boolean getShowAdvice(){
        return getCorrectAnswerCount() != 0 && (getCorrectAnswerCount()*1.0/questions.size()*1.0)*100 >= 80;
    }

    public boolean checkAnswer(String[] answeredOptionIds) {
        boolean isCorrect = true;
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion.isMultipleAnswerOptions()) {
            isCorrect = currentQuestion.verifyMultiAnswer(answeredOptionIds);
        } else {
            for (String answeredOptionId : answeredOptionIds) {
                if (!currentQuestion.verifyAnswer(answeredOptionId)) {
                    isCorrect = false;
                    break;
                }
            }
        }
        return isCorrect;
    }

    public int getCategoryCorrectAnswerCount(int categoryId) {
        Integer count = categoryCorrectAnswerCount.get(categoryId);
        if (count == null) {
            return 0;
        }
        return count;
    }

    public void incrementCategoryCorrectAnswerCount(int categoryId) {
        Integer count = categoryCorrectAnswerCount.get(categoryId);
        if (count == null) {
            count = 0;
        }
        categoryCorrectAnswerCount.put(categoryId, count + 1);
    }

    public int getNumberOfCategories() {
        Map<String, List<Question>> categoryNumberMap = questions.stream().collect(Collectors.groupingBy(Question::getCategory));
        return categoryNumberMap.keySet().size();
    }

    Map<Category, Integer> getMapQuestionAndCategory(Category[] categories, int total) {
        Map<Category, Integer> allQuestionInCategory = new HashMap<>(categories.length);
        for (Category category1 : categories) {
            if (Question.getNumOfQuestionIn(category1.getName()) != 0) {
                allQuestionInCategory.put(category1, Question.getNumOfQuestionIn(category1.getName()));
            }
        }

        Map<Category, Integer> questionAndCategory = new HashMap<>();
        for (int i = 0; i < total; i++) {
            List<Category> categoryList = new ArrayList<>(allQuestionInCategory.keySet());
            Category category = categoryList.get(i % allQuestionInCategory.size());

            if (questionAndCategory.containsKey(category)) {
                Integer counter =  questionAndCategory.get(category);
                questionAndCategory.put(category, ++counter);
            } else {
                questionAndCategory.put(category, 1);
            }

            int num = allQuestionInCategory.get(category);
            num--;
            if (num == 0) {
                allQuestionInCategory.remove(category);
            } else {
                allQuestionInCategory.put(category, num);
            }
        }

        return questionAndCategory;
    }

    public boolean answer(String[] answeredOptionIds) {
        boolean isCorrect = checkAnswer(answeredOptionIds);

        int categoryId = 0;
        String categoryIdStr =  getCurrentQuestion().getCategory();
        if (!categoryIdStr.isEmpty()) {
            categoryId = Integer.parseInt(categoryIdStr);
        }
        addAnsweredQuestionNumber();
        if (isCorrect) {
            incrementCorrectAnswerCount();
            incrementCategoryCorrectAnswerCount(categoryId);
            return true;
        }
        return false;
    }
}

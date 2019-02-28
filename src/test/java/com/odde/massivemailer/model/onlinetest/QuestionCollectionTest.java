package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class QuestionCollectionTest {
    private Category[] categories = {Category.SCRUM};

    @Test
    public void shouldReturnAnEmptyListIfThereIsNoQuestion() {
        QuestionCollect questionCollect = createQuestionCollect(0, 0);
        List<Question> questions = questionCollect.generateQuestionList(categories, 10);
        assertEquals(0, questions.size());
    }

    @Test
    public void shouldReturnAllTheQuestionsIfThereAreNoEnoughQuestions() {
        QuestionCollect questionCollect = createQuestionCollect(1, 0);
        List<Question> questions = questionCollect.generateQuestionList(categories, 10);
        assertEquals(1, questions.size());
    }

    @Test
    public void shouldReturnTheMaxNumberIfThereAreMoreQuestions() {
        QuestionCollect questionCollect = createQuestionCollect(11, 0);
        List<Question> questions = questionCollect.generateQuestionList(categories, 10);
        assertEquals(10, questions.size());
    }

    @Test
    public void shouldChooseTheQuestionsRandomly() {
        QuestionCollect questionCollect = createQuestionCollect(2, 0);
        questionCollect.setShouldShuffleQuestions(true);
        Set<String> selections = new HashSet<>();
        for(int i = 0; i < 10; i ++) {
            List<Question> questions = questionCollect.generateQuestionList(categories, 1);
            selections.add(questions.get(0).getDescription());
        }
        assertEquals(2, selections.size());
    }

    @Test
    public void shouldChooseEquallyFromTwoCategories() {
        QuestionCollect questionCollect = createQuestionCollect(10, 10);
        List<Question> questions = questionCollect.generateQuestionList(new Category[]{Category.SCRUM, Category.TECH}, 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(Category.SCRUM.getName())).count();
        assertEquals(5, scrumQuestionCount);
        assertEquals(10, questions.size());
    }

    @Test
    public void shouldChooseAtLeastOneQuestion() {
        QuestionCollect questionCollect = createQuestionCollect(10, 10);
        List<Question> questions = questionCollect.generateQuestionList(new Category[]{Category.SCRUM, Category.TECH}, 1);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(Category.SCRUM.getName())).count();
        assertEquals(1, scrumQuestionCount);
        assertEquals(1, questions.size());
    }

    @Test
    public void shouldChooseNotEquallyFromTwoCategories() {
        QuestionCollect questionCollect = createQuestionCollect(10, 1);
        List<Question> questions = questionCollect.generateQuestionList(new Category[]{Category.SCRUM, Category.TECH}, 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(Category.SCRUM.getName())).count();
        assertEquals(9, scrumQuestionCount);
        assertEquals(10, questions.size());
    }

    private QuestionCollect createQuestionCollect(int numberOfScrumQuestion, int numberOfTechQuestion) {
        List<Question> scrumQuestions = makeQuestions(numberOfScrumQuestion, Category.SCRUM.getName());
        scrumQuestions.addAll(makeQuestions(numberOfTechQuestion, Category.TECH.getName()));
        QuestionCollect questionCollect = new QuestionCollect(scrumQuestions);
        questionCollect.setShouldShuffleQuestions(false);
        return questionCollect;
    }

    private static List<Question> makeQuestions(int numberOfQuestion, String category) {
        List<Question> questions = new ArrayList<>();
        IntStream.range(0, numberOfQuestion)
                .forEach(index ->
                        questions.add(Question.createIt("description", "desc" + index,
                                "advice", "adv" + index, "category", category))
                );
        return questions;
    }

}

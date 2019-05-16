package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.factory.QuestionBuilder;
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
        QuestionCollection questionCollection = createQuestionCollection(0, 0);
        List<Question> questions = questionCollection.generateQuestionList(categories, 10);
        assertEquals(0, questions.size());
    }

    @Test
    public void shouldReturnAllTheQuestionsIfThereAreNoEnoughQuestions() {
        QuestionCollection questionCollection = createQuestionCollection(1, 0);
        List<Question> questions = questionCollection.generateQuestionList(categories, 10);
        assertEquals(1, questions.size());
    }

    @Test
    public void shouldReturnTheMaxNumberIfThereAreMoreQuestions() {
        QuestionCollection questionCollection = createQuestionCollection(11, 0);
        List<Question> questions = questionCollection.generateQuestionList(categories, 10);
        assertEquals(10, questions.size());
    }

    @Test
    public void shouldChooseTheQuestionsRandomly() {
        QuestionCollection questionCollection = createQuestionCollection(2, 0);
        questionCollection.setShouldShuffleQuestions(true);
        Set<String> selections = new HashSet<>();
        for(int i = 0; i < 10; i ++) {
            List<Question> questions = questionCollection.generateQuestionList(categories, 1);
            selections.add(questions.get(0).getDescription());
        }
        assertEquals(2, selections.size());
    }

    @Test
    public void shouldChooseEquallyFromTwoCategories() {
        QuestionCollection questionCollection = createQuestionCollection(10, 10);
        List<Question> questions = questionCollection.generateQuestionList(new Category[]{Category.SCRUM, Category.TECH}, 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(String.valueOf(Category.SCRUM.getId()))).count();
        assertEquals(5, scrumQuestionCount);
        assertEquals(10, questions.size());
    }

    @Test
    public void shouldChooseAtLeastOneQuestion() {
        QuestionCollection questionCollection = createQuestionCollection(10, 10);
        List<Question> questions = questionCollection.generateQuestionList(new Category[]{Category.SCRUM, Category.TECH}, 1);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(String.valueOf(Category.SCRUM.getId()))).count();
        assertEquals(1, scrumQuestionCount);
        assertEquals(1, questions.size());
    }

    @Test
    public void shouldChooseNotEquallyFromTwoCategories() {
        QuestionCollection questionCollection = createQuestionCollection(10, 1);
        List<Question> questions = questionCollection.generateQuestionList(new Category[]{Category.SCRUM, Category.TECH}, 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(String.valueOf(Category.SCRUM.getId()))).count();
        assertEquals(9, scrumQuestionCount);
        assertEquals(10, questions.size());
    }

    @Test
    public void shouldChooseNamingCategories() {
        QuestionCollection questionCollection = createQuestionCollection(0, 0);
        List<Question> questions = questionCollection.generateQuestionList(new Category[]{Category.SCRUM}, 10);
        assertEquals(0, questions.size());
    }

    @Test
    public void getScrumCategoryMoreThan3Whenxxxx() {
        createByCategory("Scrum");
        createByCategory("Tech");
        createByCategory("Team");
        List<Question> questions = new QuestionCollection(Question.getAll()).generateQuestionList(Category.values(), 10);
        for(int i = 0; i < 1000; i++){
            long count = questions.stream().filter(q -> q.getCategory().equals("1")).count();
            assertEquals("count:" + count, count >= 3, true);
        }
    }

    @Test
    public void getScrumCategoryMoreThan3Whenxxxx2() {
        createByCategory("Scrum");
        createByCategory("Scrum");
        createByCategory("Tech");
        List<Question> questions = new QuestionCollection(Question.getAll()).generateQuestionList(Category.values(), 10);
        for(int i = 0; i < 1000; i++){
            long count = questions.stream().filter(q -> q.getCategory().equals("1")).count();
            assertEquals("count:" + count, count >= 5, true);
        }
    }

    @Test
    public void getScrumCategoryMoreThan3Whenxxxx3() {
        createByCategory("Scrum");
        createByCategory("Scrum");
        createByCategory("Scrum");
        List<Question> questions = new QuestionCollection(Question.getAll()).generateQuestionList(Category.values(), 10);
        for(int i = 0; i < 1000; i++){
            long count = questions.stream().filter(q -> q.getCategory().equals("1")).count();
            assertEquals("count:" + count, count >= 10, true);
        }
    }



    private void createByCategory(String category) {
        int categoryId = Category.findByName(category).getId();
        for (int i = 0; i < 5; i++) {
            new QuestionBuilder()
                    .aQuestion(category, null, String.valueOf(categoryId))
                    .withCorrectOption("CorrectOption")
                    .please();
        }
    }

    private QuestionCollection createQuestionCollection(int numberOfScrumQuestion, int numberOfTechQuestion) {
        List<Question> scrumQuestions = makeQuestions(numberOfScrumQuestion, Category.SCRUM.getId());
        scrumQuestions.addAll(makeQuestions(numberOfTechQuestion, Category.TECH.getId()));
        QuestionCollection questionCollection = new QuestionCollection(scrumQuestions);
        questionCollection.setShouldShuffleQuestions(false);
        return questionCollection;
    }

    private static List<Question> makeQuestions(int numberOfQuestion, int categoryId) {
        List<Question> questions = new ArrayList<>();
        String category = String.valueOf(categoryId);
        IntStream.range(0, numberOfQuestion)
                .forEach(index ->
                        questions.add(Question.createIt("description", "desc" + index,
                                "advice", "adv" + index, "category", category))
                );
        return questions;
    }

}

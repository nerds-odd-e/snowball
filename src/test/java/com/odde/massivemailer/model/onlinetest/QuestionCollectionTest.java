package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class QuestionCollectionTest {
    private Category scrum = Category.createIt("Scrum");
    private Category tech = Category.createIt("Tech");
    private List<Category> categories = Collections.singletonList(scrum);

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
        List<Question> questions = questionCollection.generateQuestionList(asList(scrum, tech), 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(scrum)).count();
        assertEquals(10, questions.size());
        assertEquals(5, scrumQuestionCount);
    }

    @Test
    public void shouldChooseAtLeastOneQuestion() {
        QuestionCollection questionCollection = createQuestionCollection(10, 10);
        List<Question> questions = questionCollection.generateQuestionList(asList(scrum, tech), 1);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(scrum)).count();
        assertEquals(1, scrumQuestionCount);
        assertEquals(1, questions.size());
    }

    @Test
    public void shouldChooseNotEquallyFromTwoCategories() {
        QuestionCollection questionCollection = createQuestionCollection(10, 1);
        List<Question> questions = questionCollection.generateQuestionList(asList(scrum, tech), 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.getCategory().equals(scrum)).count();
        assertEquals(9, scrumQuestionCount);
        assertEquals(10, questions.size());
    }

    @Test
    public void shouldChooseNamingCategories() {
        QuestionCollection questionCollection = createQuestionCollection(0, 0);
        List<Question> questions = questionCollection.generateQuestionList(Collections.singletonList(scrum), 10);
        assertEquals(0, questions.size());
    }

    private QuestionCollection createQuestionCollection(int numberOfScrumQuestion, int numberOfTechQuestion) {
        List<Question> scrumQuestions = makeQuestions(numberOfScrumQuestion, scrum.getId());
        scrumQuestions.addAll(makeQuestions(numberOfTechQuestion, tech.getId()));
        QuestionCollection questionCollection = new QuestionCollection(scrumQuestions);
        questionCollection.setShouldShuffleQuestions(false);
        return questionCollection;
    }

    private static List<Question> makeQuestions(int numberOfQuestion, ObjectId categoryId) {
        List<Question> questions = new ArrayList<>();
        IntStream.range(0, numberOfQuestion)
                .forEach(index -> questions.add(new Question("desc" + index, "adv" + index, categoryId, false, false).save())
                );
        return questions;
    }

}

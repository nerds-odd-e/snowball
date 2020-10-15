package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.AnswerHistory;
import com.odde.snowball.model.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.IntStream;

import static com.odde.snowball.model.base.Repository.repo;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class QuestionCollectionTest {
    private Category scrum = Category.create("Scrum");
    private Category tech = Category.create("Tech");
    private List<Category> categories = Collections.singletonList(scrum);

    private User createUser(String email, String password) {
        User user = new User(email);
        user.setupPassword(password);
        user.save();
        return user;
    }

    private User createUser() {
        String password = "hogehoge";
        return createUser("answer_info2@example.com", password);
    }

    User user = createUser();

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
        for (int i = 0; i < 10; i++) {
            List<Question> questions = questionCollection.generateQuestionList(categories, 1);
            selections.add(questions.get(0).getDescription());
        }
        assertEquals(2, selections.size());
    }

    @Test
    public void shouldChooseEquallyFromTwoCategories() {
        QuestionCollection questionCollection = createQuestionCollection(10, 10);
        List<Question> questions = questionCollection.generateQuestionList(asList(scrum, tech), 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.category().equals(scrum)).count();
        assertEquals(10, questions.size());
        assertEquals(5, scrumQuestionCount);
    }

    @Test
    public void shouldChooseAtLeastOneQuestion() {
        QuestionCollection questionCollection = createQuestionCollection(10, 10);
        List<Question> questions = questionCollection.generateQuestionList(asList(scrum, tech), 1);
        long scrumQuestionCount = questions.stream().filter(q -> q.category().equals(scrum)).count();
        assertEquals(1, scrumQuestionCount);
        assertEquals(1, questions.size());
    }

    @Test
    public void shouldChooseNotEquallyFromTwoCategories() {
        QuestionCollection questionCollection = createQuestionCollection(10, 1);
        List<Question> questions = questionCollection.generateQuestionList(asList(scrum, tech), 10);
        long scrumQuestionCount = questions.stream().filter(q -> q.category().equals(scrum)).count();
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

    @Test
    public void _5日前に回答した問題が表示される() {
        //given
        QuestionCollection questionCollection = createQuestionCollection(1, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);

        Question question = repo(Question.class).findAll().get(0);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question.getId()), calendar.getTime());
        user.save();
        //when
        List<Question> questions = questionCollection.generateQuestionListForPractice(categories, 1, user);
        //then
        assertEquals(1, questions.size());
    }


    @Test
    public void _4日前に回答した問題が表示されない() {
        //given
        QuestionCollection questionCollection = createQuestionCollection(1, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -4);

        Question question = repo(Question.class).findAll().get(0);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question.getId()), calendar.getTime());
        user.save();
        //when
        List<Question> questions = questionCollection.generateQuestionListForPractice(categories, 1, user);
        //then
        assertEquals(0, questions.size());
    }

    @Test
    public void _4日前と５日前１件ずつあるときは１件取得できる() {
        //given
        QuestionCollection questionCollection = createQuestionCollection(2, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        Question question = repo(Question.class).findAll().get(0);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question.getId()), calendar.getTime());

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -4);
        Question question2 = repo(Question.class).findAll().get(1);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question2.getId()), calendar2.getTime());

        user.save();
        //when
        List<Question> questions = questionCollection.generateQuestionListForPractice(categories, 2, user);
        //then
        assertEquals(1, questions.size());
    }

    @Test
    public void _4日前５日前６日前１件ずつあるときは２件取得できる() {
        //given
        QuestionCollection questionCollection = createQuestionCollection(3, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        Question question = repo(Question.class).findAll().get(0);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question.getId()), calendar.getTime());

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -4);
        Question question2 = repo(Question.class).findAll().get(1);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question2.getId()), calendar2.getTime());

        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DATE, -6);
        Question question3 = repo(Question.class).findAll().get(2);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question3.getId()), calendar3.getTime());

        user.save();
        //when
        List<Question> questions = questionCollection.generateQuestionListForPractice(categories, 2, user);
        //then
        assertEquals(2, questions.size());
    }

    @Test
    public void _複数カテゴリで５日前が１件ずつあるときは２件取得できる() {
        //given
        QuestionCollection questionCollection = createQuestionCollection(2, 2);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        Question question = repo(Question.class).findAll().get(0);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question.getId()), calendar.getTime());

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -4);
        Question question2 = repo(Question.class).findAll().get(1);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question2.getId()), calendar2.getTime());

        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DATE, -5);
        Question question3 = repo(Question.class).findAll().get(2);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question3.getId()), calendar3.getTime());

        Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DATE, -4);
        Question question4 = repo(Question.class).findAll().get(3);
        new AnswerHistory().recordAnsweredQuestion(user, String.valueOf(question4.getId()), calendar4.getTime());

        user.save();
        //when
        List<Question> questions = questionCollection.generateQuestionListForPractice(categories, 10, user);
        //then
        assertEquals(2, questions.size());
    }

    @Test
    public void _未回答の質問が１件だけある時に1件取得できる() {
        //given
        QuestionCollection questionCollection = createQuestionCollection(1, 0);

        //when
        List<Question> questions = questionCollection.generateQuestionListForPractice(categories, 10, user);
        //then
        assertEquals(1, questions.size());
    }
}

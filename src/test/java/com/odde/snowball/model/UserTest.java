package com.odde.snowball.model;

import com.odde.TestWithDB;
import com.odde.snowball.model.onlinetest.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class UserTest {

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

    private Category createCategory() {
        Category category = new Category("xp", "", "");
        category.save();
        return category;
    }

    private Question createQuestion() {
        Category category = createCategory();
        Question question = new Question("test", "advice",category.getId(), false, true);
        QuestionOption correctOption = question.addOption(new QuestionOption("これは正解の選択肢", true));
        QuestionOption incorrectOption = question.addOption(new QuestionOption("これは不正解の選択肢", false));
        question.addOption(correctOption);
        question.addOption(incorrectOption);
        question.save();
        return question;
    }

    private Answer createAnswer() {
        List<String> selectedOptionIds = new ArrayList<>();
        selectedOptionIds.add("これが正解の選択肢");
        Question question = createQuestion();
        Answer answer = new Answer(question, selectedOptionIds);
        return answer;
    }

    private Date createDate(int year, int month, int date) {
        Calendar calender = Calendar.getInstance();
        calender.set(year, month, date);
        return  calender.getTime();
    }

    @Test
    public void testValidPassword() {
        String password = "hogehoge";
        User user = createUser("update@example.com", password);

        User dbUser = User.getUserByEmail(user.getEmail());
        assertNotNull(dbUser);
        assertTrue(dbUser.isPasswordCorrect(password));
    }

    @Test
    public void testInvalidPassword() {
        String password = "hogehoge";
        User user = createUser("update@example.com", password);

        String invalidPassword = "invalidhoge";
        User dbUser = User.getUserByEmail(user.getEmail());
        assertNotNull(dbUser);
        assertFalse(dbUser.isPasswordCorrect(invalidPassword));
    }

    @Test
    public void testFetchUserByToken() {
        String password = "hogehoge";
        User user = createUser("update@example.com", password);

        User dbUser = repo(User.class).findFirstBy("token", user.getToken());
        assertNotNull(dbUser);
    }


    @Test
    public void test_create_token_longer_than_100() {
        User user = new User("aaaaa@gmail.com");

        assertTrue(user.getToken().length() >= 100);
    }
    @Test
    public void test_randam_token() {
        User userOne = new User("aaaaa@gmail.com");
        User userTwo = new User("bbbb@gmail.com");

        assertNotEquals(userOne.getToken(), userTwo.getToken());
    }

    @Test
    public void test_no_answer_info() {
        // 新規ユーザを登録
        String password = "hogehoge";
        User user = createUser("answer_info1@example.com", password);

        // 登録したユーザをDBから取得
        User dbUser = User.getUserByEmail(user.getEmail());

        assertEquals(0, dbUser.getAnswerInfo().size());
    }

    @Test
    public void test_add_answer_info_1() {
        // 新規の質問を作成
        Category category = new Category("test_cat", "", "");
        category.save();
        Question question = new Question("test", "advice",category.getId(), false, true);
        question.save();

        Calendar calendar = Calendar.getInstance();

        // 最終回答日をセット
        calendar.set(2020, Calendar.DECEMBER, 1);
        Date lastAnsDate = calendar.getTime();

        // 次回表示予定日をセット
        calendar.set(2020, Calendar.DECEMBER, 11);
        Date nextShowDate = calendar.getTime();

        // 新しい回答情報を作成
        AnswerInfo ansInfo = new AnswerInfo(question.stringId(), lastAnsDate, 1, nextShowDate);

        // 新規ユーザを登録
        String password = "hogehoge";
        User user = createUser("answer_info2@example.com", password);
        user.addAnswerInfo(ansInfo);
        user.save();

        // 登録したユーザをDBから取得
        User dbUser = User.getUserByEmail(user.getEmail());

        // テスト（件数が同じであること）
        assertEquals(user.getAnswerInfo().size(), dbUser.getAnswerInfo().size());

        // テスト（対象が登録したAnswerInfoであること）
        AnswerInfo dbAnsInfo = dbUser.getAnswerInfo().get(0);
        assertAnswerInfoEquals(ansInfo, dbAnsInfo);
    }

    private void assertAnswerInfoEquals(AnswerInfo answerInfo_1, AnswerInfo answerInfo_2) {
        assertEquals(answerInfo_1.getCorrectCount(), answerInfo_2.getCorrectCount());
        assertEquals(answerInfo_1.getLastAnsweredDate(), answerInfo_1.getLastAnsweredDate());
        assertEquals(answerInfo_1.getNextShowDate(), answerInfo_1.getNextShowDate());
        assertEquals(answerInfo_1.getQuestionId(), answerInfo_1.getQuestionId());
    }
}
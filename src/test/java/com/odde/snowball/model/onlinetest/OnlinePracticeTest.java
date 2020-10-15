package com.odde.snowball.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(TestWithDB.class)
public class OnlinePracticeTest {

    @Test
    public void 問題が3問存在する時_2問のみ返されること() {
        User user = generateUsers(1).get(0);
        generateQuestions(3);
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, "", 2);
        int numberOfQuestions = onlineTest.getNumberOfQuestions();
        assertEquals(2, numberOfQuestions);
    }

    @Test
    public void 問題が2問存在する時_1問が自分のprivate問題です() {
        User user = generateUsers(1).get(0);
        createPrivateQuestion(user);
        generateQuestions(1);
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, "", 2);
        int numberOfQuestions = onlineTest.getNumberOfQuestions();
        assertEquals(2, numberOfQuestions);
    }

    @Test
    public void 問題が2問存在する時_1問が他の人のprivate問題です() {
        List<User> users = generateUsers(2);
        createPrivateQuestion(users.get(0));
        generateQuestions(1);
        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(users.get(1), "", 2);
        int numberOfQuestions = onlineTest.getNumberOfQuestions();
        assertEquals(1, numberOfQuestions);
    }

//    @Test
//    public void ある問題に誤答した場合１日後に同じ問題が優先的に再度表示される() {
//        List<User> users = generateUsers(1);
//        User user = users.get(0);
//        List<Question> questionList = generateQuestions(3);
//
//        Question question = questionList.get(1);
//        // ２番目の問題を、前日解いたことにする
//        Calendar calYesterday = Calendar.getInstance();
//        calYesterday.add(Calendar.DATE, -1);
//
//        Calendar calToday  = Calendar.getInstance();
//        calToday.add(Calendar.DATE, 0);
//
//        // 新しい回答情報を作成
//        AnswerInfo ansInfo = new AnswerInfo(question.stringId(), calYesterday.getTime(), 0, calToday.getTime());
//        user.addAnswerInfo(ansInfo);
//        user.save();
//
//        // When この取得してきたテストの最初に、前日誤答した優先的に表示される問題があるはず
//        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, "", 2);
//
//        // Then
//        // onlineTestの一番最初が、前日誤答した問題であれば、成功
//        assertEquals(question.stringId(), onlineTest.getQuestions().get(0).stringId());
//    }

    private void createPrivateQuestion(User user) {
        new Question("desc", "adv", new Category().save().getId(), false, false, false, user).save();
    }

    private List<User> generateUsers(int numberOfUser) {
        return IntStream.range(0, numberOfUser).mapToObj(index -> new User().save()).collect(Collectors.toList());
    }

    private List<Question> generateQuestions(int numberOfQuestion) {
        Category category = new Category().save();
        return IntStream.range(0, numberOfQuestion).mapToObj(index -> new Question("desc" + index, "adv" + index, category.getId(), false, false).save()).collect(Collectors.toList());
    }

}
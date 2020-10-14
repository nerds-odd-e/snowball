package com.odde.snowball.model;

import com.odde.snowball.controller.config.ApplicationConfiguration;
import com.odde.snowball.exception.EmailException;
import com.odde.snowball.model.base.Entity;
import com.odde.snowball.model.onlinetest.AnswerInfo;
import com.odde.snowball.service.MailService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.odde.snowball.model.base.Repository.repo;

@NoArgsConstructor
@Setter
@Getter
public class User extends Entity<User> {
    private String name;
    private String email;
    private String hashdPassword;
    private String token;

    @Valid
    private List<AnswerInfo> answerInfo = new ArrayList<>();

    public User(String email) {
        setEmail(email);
        setToken(createToken());
    }

    private static String createToken() {
        return RandomStringUtils.randomAlphanumeric(100);
    }

    public static void createUnconfirmedUser(String email1, MailService mailService) throws EmailException {
        User user = new User(email1);
        user.save();
        Mail email = new Mail();
        email.setSubject("");
        email.setContent(new ApplicationConfiguration().getRoot() + "initialPassword?token=" + user.getToken());
        email.sendMailToRecipient(email1, mailService);
    }

    public void setupPassword(String password) {
        setHashdPassword(toHashString(password));
    }

    public static User getUserByEmail(String email) {
        return repo(User.class).findFirstBy("email", email);
    }

    public boolean isPasswordCorrect(String password) {
        String userPassword = getHashdPassword();
        if (userPassword == null) {
            return false;
        }

        String hashedPassword = toHashString(password);
        return userPassword.equals(hashedPassword);
    }

    private String toHashString(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            return String.format("%040x", new BigInteger(1, result));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User fetchUserByToken(String token) {
        if (null == token) {
            return null;
        }
        return repo(User.class).findFirstBy("token", token);
    }

    public static boolean validatePassword(String password) {
        return !StringUtils.isEmpty(password);
    }

    public AnswerInfo addAnswerInfo(AnswerInfo answerInfo) {
        Optional<AnswerInfo> sameAnswerInfo = this.answerInfo.stream()
                .filter(o -> o.getQuestionId().equals(answerInfo.getQuestionId()))
                .findFirst();
        sameAnswerInfo.ifPresent(o -> this.answerInfo.remove(o));
        this.answerInfo.add(answerInfo);
        return answerInfo;
    }
}

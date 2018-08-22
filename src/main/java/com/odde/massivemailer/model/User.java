package com.odde.massivemailer.model;

import com.odde.massivemailer.controller.config.ApplicationConfiguration;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.math.BigInteger;
import java.security.MessageDigest;

@Table("users")
public class User extends ApplicationModel {
    private static final String HASHED_PASSWORD = "hashed_password";
    private static final String EMAIL = "email";

    public User(){
    }

    public User(String email) {
        set(this.EMAIL, email);
        set("token", createToken());
    }

    private static String createToken() {
        return RandomStringUtils.randomAlphanumeric(100);
    }

    public static void createUnconfirmedUser(String email1, MailService mailService) throws EmailException {
        User user = new User(email1);
        if (user.saveIt()) {
            Mail email = new Mail();
            email.setSubject("");
            email.setContent(new ApplicationConfiguration().getRoot() + "initialPassword?token=" + user.getToken());
            email.sendMailToRecipient(email1, mailService);
        }
    }

    public void setPassword(String password) {
        set(HASHED_PASSWORD, toHashString(password));
    }

    public static User getUserByEmail(String email) {
        LazyList<User> list = where("email = ?", email);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public boolean isPasswordCorrect(String password) {
        String userPassword = getHashedPassword();
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


    public String getToken() {
        return getString("token");
    }

    public String getHashedPassword() {
        return getString(HASHED_PASSWORD);
    }

    public static User fetchUserByToken(String token) {
        if (null == token) {
            return null;
        }
        return User.findFirst("token = ?", token);
    }

    public static boolean validatePassword(String password) {
        return !StringUtils.isEmpty(password);
    }

    public String getEmail() {
        return getString(this.EMAIL);
    }
}

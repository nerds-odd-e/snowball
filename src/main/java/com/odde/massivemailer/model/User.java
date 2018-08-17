package com.odde.massivemailer.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.math.BigInteger;
import java.security.MessageDigest;

@Table("users")
public class User extends ApplicationModel {
    public static final String HASHED_PASSWORD = "hashed_password";
    public User(){}

    public User(String email) {
        set("email", email);
        set("token", createToken());
    }

    private static String createToken() {
        return RandomStringUtils.randomAlphanumeric(100);
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
        String userPassword = getString(HASHED_PASSWORD);
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

    public static User fetchUserByToken(String token) {
        if (null == token) {
            return null;
        }
        return User.findFirst("token = ?", token);
    }

    public static boolean validatePassword(String password) {
        return !StringUtils.isEmpty(password);
    }
}

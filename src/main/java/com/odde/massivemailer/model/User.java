package com.odde.massivemailer.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;
import java.security.MessageDigest;

@Table("users")
public class User extends ApplicationModel {
    public User(){}

    public User(String email) {
        set("email", email);
    }

    public void setPassword(String password) {
        set("password", toHashString(password));
    }

    private String toHashString(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            return new String(digest.digest(password.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

    public static User getUserByEmail(String email) {
        LazyList<User> list = where("email = ?", email);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public boolean isPasswordCorrect(String password) {
        String userPassword = getString("password");
        if (userPassword == null) {
            return false;
        }

        return userPassword.equals(toHashString(password));
    }
}

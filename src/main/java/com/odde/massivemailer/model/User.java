package com.odde.massivemailer.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

@Table("users")
public class User extends ApplicationModel {
    public User(){}

    public User(String email) {
        set("email", email);
    }

    public void setPassword(String password) {
        set("password", password);
    }

    public static User getUserByEmail(String email) {
        LazyList<User> list = where("email = ?", email);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public String getPassword() {
        return getString("password");
    }
}

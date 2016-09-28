package com.odde.massivemailer.model;

/**
 * Created by csd on 27/9/16.
 */
public class Player {

    private int scars;

    public Player() {
        scars = 0;
    }

    public void addScar() {
        ++scars;
    }

    public int getScars() {
        return scars;
    }
}

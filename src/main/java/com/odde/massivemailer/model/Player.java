package com.odde.massivemailer.model;

public class Player {

    private int scars;
    private int position;

    public Player() {
        scars = 0;
    }

    public void addScar() {
        ++scars;
    }

    public int getScars() {
        return scars;
    }

    public void updatePosition(int numOfSteps) {
        position += numOfSteps;
    }

    public int getPosition() {
        return position;
    }
}

package com.odde.massivemailer.model;

public class Player {

    private int scars;
    private int position;
    private int dieResult;

    public Player() {
        scars = 0;
        position = 0;
        dieResult = 0;
    }

    public void addScar() {
        ++scars;
    }

    public int getScars() {
        return scars;
    }

    public void makeMove(int numOfSteps, int dieResult) {
        this.dieResult = dieResult;
        position += numOfSteps;
    }

    public int getPosition() {
        return position;
    }

    public int getDieResult() {
        return dieResult;
    }
}

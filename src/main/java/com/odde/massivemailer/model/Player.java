package com.odde.massivemailer.model;

import com.odde.emersonsgame.GameRound;

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

    public void updatePosition(int numOfSteps) {
        position += numOfSteps;
    }

    public int getPosition() {
        return position;
    }

    public int getDieResult() {
        return dieResult;
    }

    public int getNormalPlayerPosition(int numOfSteps) {
        updatePosition(numOfSteps);
        return getPosition();
    }

    public int getSuperPlayerPosition(int numOfSteps) {
        updatePosition(numOfSteps);
        addScar();
        return getPosition();
    }

    public void playNormal(GameRound game) {
        dieResult = game.rollDie();
        getNormalPlayerPosition(game.computeNormalSteps(dieResult, getScars()));
    }

    public void playSuper(GameRound game) {
        dieResult = game.rollDie();
        getSuperPlayerPosition(game.computeSuperSteps(dieResult, getScars()));
    }
}

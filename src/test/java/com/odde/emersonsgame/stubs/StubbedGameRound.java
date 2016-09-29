package com.odde.emersonsgame.stubs;

import com.odde.emersonsgame.GameRound;
import com.odde.massivemailer.model.Player;


public class StubbedGameRound implements GameRound {

    private int distance;
    private int nextRand;

    public StubbedGameRound() {
        init();
    }

    @Override
    public void init() {
        distance = 0;
        nextRand = 0;
    }

    @Override
    public int getDistance() {
        return this.distance;
    }

    @Override
    public void setDistance(int t_Dist) {
        this.distance = t_Dist;
    }

    @Override
    public int rollDie() {
        return nextRand;
    }

    @Override
    public void setRandomGeneratedNumber(int num) {
        
    }

    @Override
    public Player play(String roll, Player player) {
        return null;
    }

    public void nextRandomNumber(int num) {
        nextRand = num;
    }
}

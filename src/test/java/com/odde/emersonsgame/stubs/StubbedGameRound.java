package com.odde.emersonsgame.stubs;

import com.odde.emersonsgame.GameRound;


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
    public int computeNormalSteps(int dieResult, int scars) {
        int steps =  (dieResult % 2 == 0) ? 2 : 1 - scars;
        return (steps > 0) ? steps : 0;
    }

    @Override
    public int computeSuperSteps(int dieResult, int scars) {
        int steps = dieResult - scars;
        return (steps > 0) ? steps : 0;
    }

    @Override
    public int rollDie() {
        return nextRand;
    }

    public void nextRandomNumber(int num) {
        nextRand = num;
    }
}

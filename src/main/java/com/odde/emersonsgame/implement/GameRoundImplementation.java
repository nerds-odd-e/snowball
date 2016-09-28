package com.odde.emersonsgame.implement;

import com.odde.emersonsgame.GameRound;
import java.util.Random;

public class GameRoundImplementation implements GameRound {

    private int distance;

    public GameRoundImplementation() {
        init();
    }

    @Override
    public void init() {
        distance = 0;
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
        return (1 + (int)(Math.random() * 6));
    }
}

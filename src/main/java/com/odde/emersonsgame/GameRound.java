package com.odde.emersonsgame;

public interface GameRound {

    void init();

    int getDistance();

    void setDistance(int t_Dist);

    int computeNormalSteps(int dieResult, int scars);

    int computeSuperSteps(int dieResult, int scars);

    int rollDie();

}

package com.odde.emersonsgame;

public interface GameRound {
    int getDistance();

    void setDistance(int t_Dist);

    int rollDice(int playerID);

    int getPlayerPosition(int playerID);
}

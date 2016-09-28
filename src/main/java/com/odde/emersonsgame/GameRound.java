package com.odde.emersonsgame;

public interface GameRound {
    int getDistance();

    void setDistance(int t_Dist);

    void rollDieForPlayer(int playerID, String rollType);

    int getNormalPlayerPosition(int playerID);

    int getSuperPlayerPosition(int playerID);

    String toString();

    String getGameRound();
}

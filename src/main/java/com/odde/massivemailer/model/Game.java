package com.odde.massivemailer.model;

public interface Game {
    int getDistance();

    void setDistance(int t_Dist);

    int rollDice(int playerID);

    int getPlayerPosition(int playerID);


}

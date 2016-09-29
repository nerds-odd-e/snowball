package com.odde.emersonsgame;

import com.odde.emersonsgame.exception.GameException;
import com.odde.massivemailer.model.Player;

public interface GameRound {

    void init();

    int getDistance();

    void setDistance(int t_Dist);

    int rollDie();

    void setRandomGeneratedNumber(int num);

    Player play(String roll, Player player) throws GameException;
}

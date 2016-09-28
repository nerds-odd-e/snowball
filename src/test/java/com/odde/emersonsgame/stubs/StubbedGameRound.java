package com.odde.emersonsgame.stubs;


import com.google.gson.JsonObject;
import com.odde.emersonsgame.GameRound;
import com.odde.massivemailer.model.Player;

import java.util.ArrayList;

public class StubbedGameRound implements GameRound {

    private int distance;
    private int nextRand;
    Player player;

    public StubbedGameRound() {
        this.distance = 0;
        nextRand = 0;
        player = new Player();
    }

    @Override
    public int getDistance() {
        return this.distance;
    }

    @Override
    public void setDistance(int t_Dist) {
        this.distance = t_Dist;
    }

    public void nextRandomNumber(int num) {
        nextRand = num;
    }

    public String getGameRound() {
        JsonObject currentGameRound = new JsonObject();
        currentGameRound.addProperty("distance",   distance);
        currentGameRound.addProperty("playerPos",  player.getPosition());
        currentGameRound.addProperty("playerScar", player.getScars());
        currentGameRound.addProperty("dieResult",  nextRand);
        return currentGameRound.toString();
    }

    @Override
    public int getNormalPlayerPosition(int playerID) {
        int numOfSteps = ((nextRand%2 == 0) ? 2 : 1) - player.getScars();
        if(numOfSteps < 0) {
            numOfSteps = 0;
        }

        player.updatePosition(numOfSteps);
        return player.getPosition();
    }

    public int getSuperPlayerPosition(int playerID) {
        player.updatePosition(nextRand-player.getScars());
        player.addScar();
        return player.getPosition();
    }

    @Override
    public void rollDieForPlayer(int playerID, String rollType) {
        return;
    }


}

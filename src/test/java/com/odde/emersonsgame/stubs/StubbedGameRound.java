package com.odde.emersonsgame.stubs;


import com.odde.emersonsgame.GameRound;
import com.odde.massivemailer.model.Player;

import java.util.ArrayList;

public class StubbedGameRound implements GameRound {

    private int distance;
    private int nextRand;
    private int player1Pos;
    private ArrayList<Player> players;

    public StubbedGameRound() {
        players = new ArrayList<Player>();
        this.distance = 0;
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

    public void movePlayerForNormalMode(int position) {
        player1Pos = position;
    }

    @Override
    public int rollDice(int playerID) {
        return nextRand;
    }
 
    @Override
    public int getPlayerPosition(int playerID) {
        return player1Pos;
    }

    public void movePlayerForSuperMode(int playerIndex) {
        player1Pos = nextRand - players.get(playerIndex).getScars();
    }

    public Player getPlayerAtIndex(int playerIndex) {
        return players.get(playerIndex);
    }

    public int addPlayer() {
        players.add(new Player());
        return players.size() - 1;
    }
}

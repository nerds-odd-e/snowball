package com.odde.massivemailer.model;

/**
 * Created by wenjie on 27/9/16.
 */
public class GameForTest implements Game {

    private int distance;
    private int nextRand;
    private int player1Pos;

    public GameForTest(){
        this.distance = 0;
    }

    @Override
    public int getDistance(){
        return this.distance;
    }

    @Override
    public void setDistance(int t_Dist){
        if(t_Dist > -1){
            this.distance = t_Dist;
        }
    }

    public void nextRandomNumber(int num) {
        nextRand = num;
    }

    public void player1WillBeAt(int position) {
        player1Pos = position;
    }

    @Override
    public int player1Roll() {
        return nextRand;
    }

    @Override
    public int getPlayer1Position() {
        return player1Pos;
    }
}

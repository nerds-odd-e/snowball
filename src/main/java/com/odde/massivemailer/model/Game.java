package com.odde.massivemailer.model;

/**
 * Created by wenjie on 27/9/16.
 */
public class Game {

    private int distance;

    public Game(){
        this.distance = 0;
    }

    public int getDistance(){
        return this.distance;
    }

    public void setDistance(int t_Dist){
        if(t_Dist > -1){
            this.distance = t_Dist;
        }
    }
}

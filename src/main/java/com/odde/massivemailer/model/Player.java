package com.odde.massivemailer.model;

public class Player {

    private int scars;
    private int position;
    private int dieResult;
    private String email;
    private String ID;

    public Player() {
        scars = 0;
        position = 0;
        dieResult = 0;
        email = "";
        ID = "";
    }

    public void addScar() {
        ++scars;
    }

    public int getScars() {
        return scars;
    }

    public void makeMove(int numOfSteps, int dieResult) {
        this.dieResult = dieResult;
        position += numOfSteps;
    }

    public int getPosition() {
        return position;
    }

    public int getDieResult() {
        return dieResult;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }
}

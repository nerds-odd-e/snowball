package com.odde.emersonsgame.exception;

public class GameException extends Exception{

    public static final String INVALID_MOVE = "Invalid Move";

    public static final String INVALID_TURN = "Invalid Turn";

    public GameException(String s) {
        super(s);
    }
}

package com.odde.emersonsgame.implement;

import com.odde.emersonsgame.exception.GameException;
import com.odde.massivemailer.model.Player;

public class GameRound {

    public static final String ROLL_NORMAL = "normal";
    public static final String ROLL_SUPER = "super";
    public static final int DIE_MAXROLL = 6;
    public static final int DIE_MINROLL = 1;
    private int distance;
    private int randomNum = 0;

    public GameRound() {
        init();
    }

    public void init() {
        distance = 0;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int t_Dist) {
        this.distance = t_Dist;
    }

    public int rollDie() {
        if(randomNum == 0)
            return DIE_MINROLL + (int)(Math.random() * DIE_MAXROLL);
        return randomNum;
    }

    public void setRandomGeneratedNumber(int num) {
        randomNum = num;
    }

    public Player play(String rollType, Player player) throws GameException {
        if (!ROLL_NORMAL.equals(rollType) && !ROLL_SUPER.equals(rollType)) {
            throw new GameException(GameException.INVALID_MOVE);
        }

        int die = rollDie();
        int steps = -player.getScars();

        if(ROLL_NORMAL.equals(rollType)) {
            steps = Math.max(0, steps + ((die % 2 == 0) ? 2 : 1));
        }
        if (ROLL_SUPER.equals(rollType)) {
            steps = Math.max(0, steps + die);
            player.addScar();
        }

        player.makeMove(steps, die);
        return player;
    }
}

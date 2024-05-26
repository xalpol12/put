package com.xalpol12.wsserver.model.internal;

import lombok.Data;

@Data
public class GameTimer {
    private final int ROUND_LENGTH;
    private int gameTimer;

    public GameTimer(int roundLength) {
        ROUND_LENGTH = roundLength;
    }

    public int decreaseTime() {
        if (gameTimer <= 0) return 0;
        return gameTimer--;
    }

    public void startNewRound() {
        gameTimer = ROUND_LENGTH;
    }
}

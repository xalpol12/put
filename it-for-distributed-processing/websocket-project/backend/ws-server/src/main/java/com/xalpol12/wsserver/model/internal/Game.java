package com.xalpol12.wsserver.model.internal;

import lombok.Getter;

public class Game {
    private final GameTimer gameTimer;

    private int roundCounter;
    @Getter
    private String currentWord = "TEST";
    private String userWithDrawingPermissions; // TODO: implement changing drawing fellas

    public Game(int roundLength) {
        gameTimer = new GameTimer(roundLength);
    }

    public void startGame() {
        assignNewWord();
    }

    public void tick() {
        int currentRoundTime = gameTimer.decreaseTime();
        if (currentRoundTime == 0) {
            startNewRound();
        } else {
        }
    }

    private void assignNewWord() {
        currentWord = WordGenerator.getNewWord();
    }

    private void startNewRound() {
        if (hasGameFinished()) {
            // signal frontend to display summary
        } else {
            roundCounter--;
            gameTimer.startNewRound();
        }
    }

    private boolean hasGameFinished() {
        return roundCounter == 0;
    }
}

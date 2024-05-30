package com.xalpol12.wsserver.model.internal;

public class Game {
    private final GameTimer gameTimer;

    private final int ROUNDS_IN_GAME;
    private int roundCounter;
    private String currentWord;
    private String userWithDrawingPermissions; // TODO: implement changing drawing fellas

    public Game(int numberOfRounds, int roundLength) {
        ROUNDS_IN_GAME = numberOfRounds;
        gameTimer = new GameTimer(roundLength);
    }

    public void startGame() {
        roundCounter = ROUNDS_IN_GAME;
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

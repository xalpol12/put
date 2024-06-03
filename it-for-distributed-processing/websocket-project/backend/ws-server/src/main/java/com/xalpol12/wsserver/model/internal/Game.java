package com.xalpol12.wsserver.model.internal;

import com.xalpol12.wsserver.utils.WordPool;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final GameTimer gameTimer;
    private final List<String> players = new ArrayList<>();
    private final Random random = new Random();
    @Getter
    private GameState gameState = GameState.CREATED;
    // private int roundCounter;
    @Getter
    private String currentWord = "TEST";
    private String drawer = "TEST";

    public Game(int roundLength) {
        gameTimer = new GameTimer(roundLength);
    }

    public void startGame() {
        gameState = GameState.IN_PROGRESS;
        startNewRound();
    }

    public void finishGame() {
        gameState = GameState.FINISHED;
    }

    public void tick() {
        int currentRoundTime = gameTimer.decreaseTime();
        if (currentRoundTime == 0) {
            startNewRound();
        }
    }

    private void startNewRound() {
        gameTimer.startNewRound();
        assignNewWord();
        assignNewDrawer();
    }

    private void assignNewWord() {
        currentWord = WordPool.getNextWord(currentWord);
    }

    private void assignNewDrawer() {
        String previous = drawer;
        do {
            int index = random.nextInt(players.size());
            drawer = players.get(index);
        } while (drawer.equals(previous));
    }

    public void addPlayer(String userId) {
        players.add(userId);
    }

    // private boolean hasGameFinished() {
    //     return roundCounter == 0;
    // }
}

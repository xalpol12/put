package com.xalpol12.wsserver.model.internal;

import com.xalpol12.wsserver.events.GameTimeUpdateEvent;
import com.xalpol12.wsserver.events.NewWordEvent;
import com.xalpol12.wsserver.events.ScoreUpdateEvent;
import com.xalpol12.wsserver.model.PlayerData;
import com.xalpol12.wsserver.service.GameSocketService;
import com.xalpol12.wsserver.utils.WordPool;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Game {
    private final GameTimer gameTimer;
    private final Map<String, PlayerData> playersData = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final String sessionId;
    private final GameSocketService listener;

    @Getter
    private GameState gameState = GameState.CREATED;
    // private int roundCounter;
    @Getter
    private String currentWord = "TEST";
    private String drawer = "TEST";

    public Game(GameSocketService listener, String userId, String sessionId, int roundLength) {
        this.listener = listener;
        this.sessionId = sessionId;
        gameTimer = new GameTimer(roundLength);
        addPlayer(userId);
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
        notifyTimeUpdate(currentRoundTime);
        if (currentRoundTime == 0) {
            startNewRound();
        }
    }

    private void startNewRound() {
        assignNewWord();
        assignNewDrawer();
        notifyNewWord(currentWord, drawer);
        gameTimer.startNewRound();
    }

    private void assignNewWord() {
        currentWord = WordPool.getNextWord(currentWord);
    }

    private void assignNewDrawer() {
        List<String> userList = new ArrayList<>(playersData.keySet());
        String previous = drawer;
        do {
            int index = random.nextInt(0, userList.size());
            drawer = userList.get(index);
        } while (drawer.equals(previous));
    }

    public void addPlayer(String userId) {
        playersData.put(userId, new PlayerData());
        if (gameState == GameState.CREATED) {
            startGame();
        }
    }

    public void modifyPlayerData(String userId, PlayerData data) {
        playersData.put(userId, data);
    }

    public PlayerData getPlayerData(String userId) {
        return playersData.get(userId);
    }

    public boolean playerExists(String userId) {
        return playersData.containsKey(userId);
    }

    // private boolean hasGameFinished() {
    //     return roundCounter == 0;
    // }

    private void notifyTimeUpdate(int remainingTime) {
        GameTimeUpdateEvent event = new GameTimeUpdateEvent(sessionId, remainingTime);
        listener.onGameTimeUpdate(event);
    }

    private void notifyNewWord(String newWord, String newDrawer) {
        NewWordEvent event = new NewWordEvent(sessionId, newWord, newDrawer);
        listener.onNewWord(event);
    }

    private void notifyScoreUpdate(Map<String, PlayerData> playersData) {
        ScoreUpdateEvent event = new ScoreUpdateEvent(sessionId, playersData);
        listener.onScoreUpdate(event);
    }
}

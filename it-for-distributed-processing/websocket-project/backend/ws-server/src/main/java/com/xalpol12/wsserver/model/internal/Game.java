package com.xalpol12.wsserver.model.internal;

import com.xalpol12.wsserver.events.GameEventListener;
import com.xalpol12.wsserver.events.GameTimeUpdateEvent;
import com.xalpol12.wsserver.events.NewWordEvent;
import com.xalpol12.wsserver.events.ScoreUpdateEvent;
import com.xalpol12.wsserver.model.PlayerData;
import com.xalpol12.wsserver.utils.WordPool;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    private final GameTimer gameTimer;
    private final Map<String, PlayerData> playersData = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final String sessionId;
    private final List<GameEventListener> listeners = new CopyOnWriteArrayList<>();
    @Getter
    private GameState gameState = GameState.CREATED;
    // private int roundCounter;
    @Getter
    private String currentWord = "TEST";
    private String drawer = "TEST";

    public Game(String sessionId, int roundLength) {
        this.sessionId = sessionId;
        gameTimer = new GameTimer(roundLength);
    }

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameEventListener listener) {
        listeners.remove(listener);
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
        gameTimer.startNewRound();
    }

    private void assignNewWord() {
        currentWord = WordPool.getNextWord(currentWord);
    }

    private void assignNewDrawer() {
        List<String> userList = new ArrayList<>(playersData.keySet());
        String previous = drawer;
        do {
            int index = random.nextInt(userList.size());
            drawer = userList.get(index);
        } while (drawer.equals(previous));
    }

    public void addPlayer(String userId) {
        playersData.put(userId, new PlayerData());
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
        for (GameEventListener l : listeners) {
            l.onGameTimeUpdate(event);
        }
    }

    private void notifyNewWord(String newWord, String newDrawer) {
        NewWordEvent event = new NewWordEvent(sessionId, newWord, newDrawer);
        for (GameEventListener l : listeners) {
            l.onNewWord(event);
        }
    }

    private void notifyScoreUpdate(Map<String, PlayerData> playersData) {
        ScoreUpdateEvent event = new ScoreUpdateEvent(sessionId, playersData);
        for (GameEventListener l : listeners) {
            l.onScoreUpdate(event);
        }
    }
}

package com.xalpol12.wsserver.model.internal;

import com.xalpol12.wsserver.events.GameTimeUpdateEvent;
import com.xalpol12.wsserver.events.NewWordEvent;
import com.xalpol12.wsserver.events.ScoreUpdateEvent;
import com.xalpol12.wsserver.model.PlayerData;
import com.xalpol12.wsserver.service.GameSocketService;
import com.xalpol12.wsserver.utils.WordPool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Game {
    private final GameTimer gameTimer;
    private final Map<String, PlayerData> playersData = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final String sessionId;
    private final GameSocketService listener;

    @Getter
    private GameState gameState = GameState.CREATED;

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

    public boolean hasUserGuessedCorrectly(String userId, String userGuess) {
        if (userGuess.equalsIgnoreCase(getCurrentWord())) {
            incrementPlayerPoints(userId);
            //TODO: change flag for already guessed in this round
            return true;
        } else {
            return false;
        }
    }

    private void incrementPlayerPoints(String userId) {
        if (playerExists(userId)) {
            PlayerData playerData = playersData.get(userId);
            playerData.incrementScore();
            modifyPlayerData(userId, playerData);
            log.info("Incremented user's {} score, current: {}", userId, playerData.getScore());
            notifyScoreUpdate(userId, playerData.getScore());
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
        if (playersData.size() >= 2) {
            notifyScoreUpdate(userId, 0);
        }
        if (gameState == GameState.CREATED && playersData.size() >= 2) {
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

    public Map<String, PlayerData> getAllPlayersData() {
        return playersData;
    }

    private void notifyTimeUpdate(int remainingTime) {
        GameTimeUpdateEvent event = new GameTimeUpdateEvent(sessionId, remainingTime);
        listener.onGameTimeUpdate(event);
    }

    private void notifyNewWord(String newWord, String newDrawer) {
        NewWordEvent event = new NewWordEvent(sessionId, newWord, newDrawer);
        listener.onNewWord(event);
    }

    private void notifyScoreUpdate(String userId, Integer currentScore) {
        ScoreUpdateEvent event = new ScoreUpdateEvent(sessionId, userId, currentScore);
        listener.onScoreUpdate(event);
    }
}

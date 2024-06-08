package com.xalpol12.wsserver.model;

import com.xalpol12.wsserver.exception.ClientNotFoundException;
import com.xalpol12.wsserver.model.internal.Game;
import com.xalpol12.wsserver.model.internal.GameState;
import com.xalpol12.wsserver.protos.ChatMessagePayload;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Data
public class GameSession {
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    @Getter
    private final List<String> drawnFrames = new CopyOnWriteArrayList<>();
    private final Game game;

    public void addWebSocketSessionToSession(WebSocketSession session) {
        log.info("WebSocketSession {} added to a set", session.getId());
        sessions.add(session);
    }

    public void removeWebSocketSessionFromSession(WebSocketSession session) {
        sessions.remove(session);
        log.info("WebSocketSession {} removed from a set", session.getId());
    }

    public Set<WebSocketSession> getWebSocketSessions() {
        return sessions;
    }

    public void addUserToSession(String clientId) {
        game.addPlayer(clientId);
    }

    public PlayerData getUserData(String clientId) {
        if (game.playerExists(clientId)) {
            return game.getPlayerData(clientId);
        } else {
            throw new ClientNotFoundException("Client with id: " + clientId + " not found");
        }
    }

    public void addToDrawnFrames(String message) {
        drawnFrames.add(message);
        log.info("Currently frames: {}", drawnFrames.size());
    }

    public void clearDrawnFrames() {
        drawnFrames.clear();
    }

    public Map<String, PlayerData> getAllPlayersData() {
        return game.getAllPlayersData();
    }

    public boolean hasDrawingPermission(String userId) {
        return true; // TODO: Add checking drawing permission
    }

    public ChatMessagePayload processMessage(ChatMessagePayload message) {
        if (game.hasUserGuessedCorrectly(message.getSender(), message.getContent())) {
            return ChatMessagePayload.newBuilder()
                    .setSender("SERVER")
                    .setContent("Good guess!")
                    .build();
        } else {
            return message;
        }
    }

    public GameState getGameState() {
        return game.getGameState();
    }
}

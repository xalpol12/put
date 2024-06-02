package com.xalpol12.wsserver.model;

import com.xalpol12.wsserver.exception.ClientNotFoundException;
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
    private final Map<String, UserData> usersData = new ConcurrentHashMap<>();
    @Getter
    private final List<String> drawnFrames = new CopyOnWriteArrayList<>();

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
        usersData.put(clientId, new UserData());
    }

    public UserData getUserData(String clientId) {
        if (usersData.containsKey(clientId)) {
            return usersData.get(clientId);
        } else {
            throw new ClientNotFoundException("Client with id: " + clientId + " not found");
        }
    }

    public void addToDrawnFrames(String message) {
        drawnFrames.add(message);
        log.info("Currently frames: {}", drawnFrames.size());
    }

    public boolean hasDrawingPermission(String userId) {
        return true; // TODO: Add checking drawing permission
    }
}

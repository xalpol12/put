package com.xalpol12.wsserver.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Service
public class SessionService {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>(); // game sessions (chat, scores, etc.)
    // do not track /join and /draw sessions as the

    private final List<TextMessage> drawnFrames = new CopyOnWriteArrayList<>();

    public void addToSessions(String clientId, WebSocketSession session) {
        sessions.put(clientId, session);
    }

    public WebSocketSession getSession(String clientId) {
        return sessions.get(clientId);
    }

    public Collection<WebSocketSession> getSessions() {
        return sessions.values();
    }

    public boolean existsById(String clientId) {
        return sessions.containsKey(clientId);
    }

    public int getSavedSessionsCount() {
        return sessions.size();
    }

    public void addToDrawnFrames(TextMessage message) {
        drawnFrames.add(message);
    }

    public boolean isDrawnFramesEmpty() {
        return drawnFrames.isEmpty();
    }

}

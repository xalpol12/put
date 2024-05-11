package com.xalpol12.wsserver.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Service
public class SessionService {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private final List<TextMessage> drawnFrames = new CopyOnWriteArrayList<>();

    public void addToSessions(WebSocketSession session) {
        sessions.add(session);
    }

    public boolean existsById(String sessionId) {
        return sessions.stream()
                .anyMatch(x -> x.getId().equals(sessionId));
    }

    public int getSavedSessionsCount() {
        return sessions.size();
    }

    public void addToDrawnFrames(TextMessage message) {
        drawnFrames.add(message);
    }

}

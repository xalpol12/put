package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.exception.SessionAlreadyExistsException;
import com.xalpol12.wsserver.exception.SessionDoesNotExistException;
import com.xalpol12.wsserver.model.GameSession;
import com.xalpol12.wsserver.model.UserData;
import com.xalpol12.wsserver.model.dto.SessionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class GameSessionService {
    private final Map<String, GameSession> keysSessions = new ConcurrentHashMap<>();

    public String addNewSession(SessionDTO sessionDTO) {
        String sessionId = sessionDTO.sessionId();
        if (!keysSessions.containsKey(sessionId)) {
            log.info("Created new session with id: {}", sessionId);
            keysSessions.put(sessionId, new GameSession());
            return sessionId;
        } else {
            log.error("Session with id: {} already exists!", sessionId);
            throw new SessionAlreadyExistsException("Session with id: " + sessionId + " already exists");
        }
    }

    public void addUserToSession(String clientId, String sessionId) {
        GameSession gs = getSessionById(sessionId);
        gs.addUserToSession(clientId);
        log.info("Added user: {} to session: {}", clientId, sessionId);
    }

    private GameSession getSessionById(String sessionId) {
        if (keysSessions.containsKey(sessionId)) {
            return keysSessions.get(sessionId);
        } else {
            log.error("Session with id: {} does not exist", sessionId);
            throw new SessionDoesNotExistException("Session with id: " + sessionId + " does not exist");
        }
    }

    public UserData getUserData(String sessionId, String clientId) {
        GameSession gs = getSessionById(sessionId);
        return gs.getUserData(clientId);
    }

    public void addToDrawnFrames(String sessionId, TextMessage message) {
        GameSession gs = getSessionById(sessionId);
        log.info("Added new frame to session: {}", sessionId);
        gs.addToDrawnFrames(message);
    }

    public boolean isDrawnFramesEmpty(String sessionId) {
        GameSession gs = getSessionById(sessionId);
        return gs.isDrawnFramesEmpty();
    }
}

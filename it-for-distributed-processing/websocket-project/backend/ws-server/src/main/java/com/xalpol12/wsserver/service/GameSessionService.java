package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.exception.SessionAlreadyExistsException;
import com.xalpol12.wsserver.exception.SessionDoesNotExistException;
import com.xalpol12.wsserver.model.GameSession;
import com.xalpol12.wsserver.model.dto.SessionDTO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class GameSessionService {

    private final Map<String, GameSession> keysSessions = new ConcurrentHashMap<>();

    public String addNewSession(SessionDTO sessionDTO) {
        String sessionId = sessionDTO.sessionId();
        if (!keysSessions.containsKey(sessionId)) {
            keysSessions.put(sessionId, new GameSession());
            return sessionId;
        } else {
            throw new SessionAlreadyExistsException("Session with id: " + sessionId + " already exists");
        }
    }

    public void addUserToSession(String clientId, String sessionId) {
        GameSession gs = getSessionById(sessionId);
        gs.addUserToSession(clientId);
    }

    private GameSession getSessionById(String sessionId) {
        if (keysSessions.containsKey(sessionId)) {
            return keysSessions.get(sessionId);
        } else {
            throw new SessionDoesNotExistException("Session with id: " + sessionId + " does not exist");
        }
    }

//    public UserData getUserData(String clientId) {
//        return usersData.get(clientId);
//    }

    public void addToDrawnFrames(String sessionId, TextMessage message) {
        GameSession gs = getSessionById(sessionId);
        gs.addToDrawnFrames(message);
    }

    public boolean isDrawnFramesEmpty(String sessionId) {
        GameSession gs = getSessionById(sessionId);
        return gs.isDrawnFramesEmpty();
    }
}

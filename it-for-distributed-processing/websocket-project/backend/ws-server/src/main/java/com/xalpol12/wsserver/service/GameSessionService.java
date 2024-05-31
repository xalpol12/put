package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.exception.ClientNotFoundException;
import com.xalpol12.wsserver.exception.SessionAlreadyExistsException;
import com.xalpol12.wsserver.exception.SessionDoesNotExistException;
import com.xalpol12.wsserver.model.GameSession;
import com.xalpol12.wsserver.model.UserData;
import com.xalpol12.wsserver.model.dto.SessionDTO;
import com.xalpol12.wsserver.model.message.payload.HandshakePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class GameSessionService {
    private final Map<String, GameSession> keysSessions = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, HandshakePayload> webSocketSessionsIds = new ConcurrentHashMap<>(); // TODO: Possibly change wssession to string key?

    // GameSessions : Users access methods
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
        GameSession gs = getGameSessionById(sessionId);
        gs.addUserToSession(clientId);
        log.info("Added user: {} to session: {}", clientId, sessionId);
    }

    private GameSession getGameSessionById(String sessionId) {
        if (keysSessions.containsKey(sessionId)) {
            return keysSessions.get(sessionId);
        } else {
            log.error("Session with id: {} does not exist", sessionId);
            throw new SessionDoesNotExistException("Session with id: " + sessionId + " does not exist");
        }
    }

    public UserData getUserData(String sessionId, String clientId) {
        GameSession gs = getGameSessionById(sessionId);
        return gs.getUserData(clientId);
    }

    public boolean hasDrawingPermission(WebSocketSession session) {
        HandshakePayload ids = getIdsByWebSocketSession(session);
        GameSession gs = getGameSessionById(ids.getSessionId());
        return gs.hasDrawingPermission(ids.getClientId());
    }

    // WebSocketSessions : Ids access methods
    public void mapWebSocketSession(WebSocketSession session, HandshakePayload payload) {
        webSocketSessionsIds.put(session, payload);
        GameSession gs = getGameSessionById(payload.getSessionId());
        gs.addWebSocketSessionToSession(session);
        log.info("Mapped session: {} to clientId: {} and sessionId: {}",
                session.getId(), payload.getClientId(), payload.getSessionId());
    }

    public void removeWebSocketSessionFromMap(WebSocketSession session) {
        HandshakePayload ids = webSocketSessionsIds.remove(session);
        if (ids != null) {
            GameSession gs = getGameSessionById(ids.getSessionId());
            gs.removeWebSocketSessionFromSession(session);
            log.info("Removed session: {} from mapping, previously associated with session: {} and key: {}",
                    session.getId(), ids.getSessionId(), ids.getClientId());
        }
    }

    private HandshakePayload getIdsByWebSocketSession(WebSocketSession session) {
        if (webSocketSessionsIds.containsKey(session)) {
            return webSocketSessionsIds.get(session);
        } else {
            throw new ClientNotFoundException("Session with id: " + session.getId() + "not found in the mapping session:ids");
        }
    }

    public Set<WebSocketSession> getAllWSSessionsFromGameSessionByWSSession(WebSocketSession session) {
        HandshakePayload ids = getIdsByWebSocketSession(session);
        GameSession gs = getGameSessionById(ids.getSessionId());
        return gs.getWebSocketSessions();
    }

    // Frames access methods
    public void addToDrawnFrames(WebSocketSession session, TextMessage message) {
        HandshakePayload ids = getIdsByWebSocketSession(session);
        GameSession gs = getGameSessionById(ids.getSessionId());
        log.info("Added new frame to session: {}", ids.getSessionId());
        gs.addToDrawnFrames(message);
    }

    public List<TextMessage> getDrawnFrames(String sessionId) {
        GameSession gs = getGameSessionById(sessionId);
        return gs.getDrawnFrames();
    }
}

package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.exception.ClientNotFoundException;
import com.xalpol12.wsserver.exception.SessionAlreadyExistsException;
import com.xalpol12.wsserver.exception.SessionDoesNotExistException;
import com.xalpol12.wsserver.model.GameSession;
import com.xalpol12.wsserver.model.PlayerData;
import com.xalpol12.wsserver.model.dto.SessionDTO;
import com.xalpol12.wsserver.model.dto.SessionResponse;
import com.xalpol12.wsserver.model.internal.Game;
import com.xalpol12.wsserver.model.internal.GameState;
import com.xalpol12.wsserver.protos.ChatMessagePayload;
import com.xalpol12.wsserver.protos.GameScorePayload;
import com.xalpol12.wsserver.protos.HandshakePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class GameSessionService {

    private final Map<String, GameSession> keysSessions = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, HandshakePayload> webSocketSessionsIds = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    private ApplicationContext ctx;

    public GameSessionService() {
        scheduler.scheduleAtFixedRate(this::gameLoop, 0, 1, TimeUnit.SECONDS);
    }

    private void gameLoop() {
        for (GameSession session : keysSessions.values()) {
            if (session.getGameState() == GameState.IN_PROGRESS) {
                session.getGame().tick();
            }
        }
    }

    // GameSessions : Users access methods
    public SessionResponse addNewSession(SessionDTO sessionDTO) {
        String sessionId = sessionDTO.sessionId();
        String userId = sessionDTO.userId();
        if (!keysSessions.containsKey(sessionId)) {
            log.info("Created new session with id: {}", sessionId);
            GameSocketService gss = ctx.getBean(GameSocketService.class);
            keysSessions.put(sessionId, new GameSession(new Game(gss, userId, sessionId, 30)));
            return new SessionResponse(sessionDTO.userId(), sessionDTO.sessionId());
        } else {
            log.error("Session with id: {} already exists!", sessionId);
            throw new SessionAlreadyExistsException("Session with id: " + sessionId + " already exists");
        }
    }

    public SessionResponse addUserToSession(String userId, String sessionId) {
        GameSession gs = getGameSessionById(sessionId);
        gs.addUserToSession(userId);
        log.info("Added user: {} to session: {}", userId, sessionId);
        return new SessionResponse(userId, sessionId);
    }

    private GameSession getGameSessionById(String sessionId) {
        if (keysSessions.containsKey(sessionId)) {
            return keysSessions.get(sessionId);
        } else {
            log.error("Session with id: {} does not exist", sessionId);
            throw new SessionDoesNotExistException("Session with id: " + sessionId + " does not exist");
        }
    }

    public PlayerData getUserData(String sessionId, String clientId) {
        GameSession gs = getGameSessionById(sessionId);
        return gs.getUserData(clientId);
    }

    public boolean hasDrawingPermission(WebSocketSession session) {
        HandshakePayload ids = getIdsByWebSocketSession(session);
        GameSession gs = getGameSessionById(ids.getSessionId());
        return gs.hasDrawingPermission(ids.getUserId());
    }

    // WebSocketSessions : Ids access methods
    public void mapWebSocketSession(WebSocketSession session, HandshakePayload payload) {
        webSocketSessionsIds.put(session, payload);
        GameSession gs = getGameSessionById(payload.getSessionId());
        gs.addWebSocketSessionToSession(session);
        log.info("Mapped session: {} to clientId: {} and sessionId: {}",
                session.getId(), payload.getUserId(), payload.getSessionId());
    }

    public void removeWebSocketSessionFromMap(WebSocketSession session) {
        HandshakePayload ids = webSocketSessionsIds.remove(session);
        if (ids != null) {
            GameSession gs = getGameSessionById(ids.getSessionId());
            gs.removeWebSocketSessionFromSession(session);
            log.info("Removed session: {} from mapping, previously associated with session: {} and key: {}",
                    session.getId(), ids.getSessionId(), ids.getUserId());
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

    public Set<WebSocketSession> getAllWSSessionsBySessionId(String sessionId) {
        GameSession gs = getGameSessionById(sessionId);
        return gs.getWebSocketSessions();
    }

    // Frames access methods
    public void addToDrawnFrames(WebSocketSession session, String message) {
        HandshakePayload ids = getIdsByWebSocketSession(session);
        GameSession gs = getGameSessionById(ids.getSessionId());
        log.info("Added new frame to session: {}", ids.getSessionId());
        gs.addToDrawnFrames(message);
    }

    public List<String> getDrawnFrames(String sessionId) {
        GameSession gs = getGameSessionById(sessionId);
        return gs.getDrawnFrames();
    }

    public void clearDrawnFrames(String sessionId) {
        GameSession gs = getGameSessionById(sessionId);
        gs.clearDrawnFrames();
    }

    public ChatMessagePayload processMessage(WebSocketSession session, ChatMessagePayload message) {
        HandshakePayload ids = getIdsByWebSocketSession(session);
        GameSession gs = getGameSessionById(ids.getSessionId());
        return gs.processMessage(message);
    }

    public List<GameScorePayload> getAllPlayersData(String sessionId) {
        GameSession gs = getGameSessionById(sessionId);
        Map<String, PlayerData> playersData = gs.getAllPlayersData();
        List<GameScorePayload> payload = new ArrayList<>();
        playersData.forEach((id, data) -> {
            GameScorePayload gsp = GameScorePayload.newBuilder()
                    .setUserId(id)
                    .setScore(data.getScore())
                    .build();
            payload.add(gsp);
        });
        return payload;
    }
}

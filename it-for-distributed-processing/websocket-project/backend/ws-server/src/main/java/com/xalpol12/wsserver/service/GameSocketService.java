package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.model.message.payload.*;
import com.xalpol12.wsserver.sender.GameSocketSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameSocketService {
    private final GameSessionService gameSessionService;
    private final GameSocketSender sender;

    public void handleHandshakeMessage(WebSocketSession session, HandshakePayload payload) throws IOException {
        gameSessionService.mapWebSocketSession(session, payload);
        List<TextMessage> drawnFrames = gameSessionService.getDrawnFrames(payload.getSessionId());
        sender.sendMessages(session, drawnFrames);
    }

    public void handleDrawingMessage(WebSocketSession session, DrawingPayload payload) throws IOException {
        if (!gameSessionService.hasDrawingPermission(session)) return;
        gameSessionService.addToDrawnFrames(session, payload.getDrawingFrame());
        String senderId = session.getId();
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsFromGameSessionByWSSession(session);
        for (WebSocketSession s : sessions) {
            String sessionId = s.getId();
            if (!sessionId.equals(senderId)) { // do not transmit to the one that sent a frame
                s.sendMessage(payload.getDrawingFrame());
            }
        }
        log.info("Transmitted frame to {} clients", sessions.size() - 1);
    }

    public void handleChatMessage(WebSocketSession session, ChatMessagePayload payload) {

    }

    public void handleGameDataMessage(WebSocketSession session, GameDataPayload payload) {

    }

    public void handleGameTimerMessage(WebSocketSession session, GameTimerPayload payload) {

    }

    public void removeWebSocketSessionFromMap(WebSocketSession session) {
        gameSessionService.removeWebSocketSessionFromMap(session);
    }
}

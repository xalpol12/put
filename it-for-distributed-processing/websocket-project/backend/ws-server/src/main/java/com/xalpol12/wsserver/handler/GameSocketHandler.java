package com.xalpol12.wsserver.handler;

import com.xalpol12.wsserver.model.message.payload.*;
import com.xalpol12.wsserver.service.GameSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameSocketHandler extends TextWebSocketHandler {

    private final GameSessionService sessionService;
    private final List<WebSocketSession> drawingSessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        drawingSessions.add(session);
    }

    //TODO: fix
    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session,
                                     @NotNull TextMessage message) throws Exception {
    }

    private void handleHandshakeMessage(WebSocketSession session, HandshakePayload payload) {
        // add to map session -> session id, client id; then each message from given session will be recognizable
        // send drawing board history

    }

    private void handleDrawingMessage(WebSocketSession session, DrawingPayload payload) {
        if (!hasDrawingPermission("id")) return;
        sessionService.addToDrawnFrames(message); // save frame in history
        for (WebSocketSession s : drawingSessions) {
            if (!session.getId().equals(s.getId())) { // do not transmit to the one that sent a frame
                s.sendMessage(message);
            }
        }
        log.info("Message {} sent to {} clients", message, drawingSessions.size() - 1);

    }

    private void handleChatMessage(WebSocketSession session, ChatMessagePayload payload) {

    }

    private void handleGameDataMessage(WebSocketSession session, GameDataPayload payload) {

    }

    private void handleGameTimerMessage(WebSocketSession session, GameTimerPayload payload) {

    }

    private boolean hasDrawingPermission(String clientId) {
        // TODO: Implement
        return true;
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session,
                                      @NotNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        drawingSessions.remove(session);
    }
}

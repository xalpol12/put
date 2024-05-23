package com.xalpol12.wsserver.handler;

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
public class DrawingSocketHandler extends TextWebSocketHandler {

    private final GameSessionService sessionService;
    private final List<WebSocketSession> drawingSessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        drawingSessions.add(session);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session,
                                     @NotNull TextMessage message) throws Exception {
        if (!hasDrawingPermission("id")) return;
        sessionService.addToDrawnFrames(message); // save frame in history
        for (WebSocketSession s: drawingSessions) {
            if (!session.getId().equals(s.getId())) { // do not transmit to the one that sent a frame
                s.sendMessage(message);
            }
        }
        log.info("Message {} sent to {} clients", message, drawingSessions.size() - 1);
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

package com.xalpol12.wsserver.handler;

import com.xalpol12.wsserver.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DrawingSocketHandler extends TextWebSocketHandler {

    private final SessionService sessionService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sessionService.addToDrawnFrames(message); // save frame in history
        Collection<WebSocketSession> sessions = sessionService.getSessions();
        for (WebSocketSession s: sessions) {
            if (!session.getId().equals(s.getId())) { // do not transmit to the one that sent a frame
                s.sendMessage(message);
            }
        }
        log.info("Message {} sent to {} clients", message, sessions.size());
    }
}

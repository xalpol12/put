package com.xalpol12.wsserver.handler;

import com.xalpol12.wsserver.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RejoinSocketHandler extends TextWebSocketHandler {

    private final SessionService sessionService;

    // Receive saved sessionId from client:
    // if session was saved then return frames
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String sessionId = message.getPayload();
        if (sessionService.existsById(sessionId)) {
            List<TextMessage> drawnFrames = sessionService.getDrawnFrames();
            for (TextMessage t : drawnFrames) {
                session.sendMessage(t);
            }
            log.info("{} rejoined, sent {} frames", sessionId, drawnFrames.size());
        }
    }
}

package com.xalpol12.wsserver.handler;

import com.xalpol12.wsserver.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JoinSocketHandler extends TextWebSocketHandler {

    private final SessionService sessionService;

    // clients sends clientId, server adds session if new and retransmits frames (in both cases)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String clientId = message.getPayload();
        if (!sessionService.existsById(clientId)) {
            sessionService.addToSessions(clientId, session);
            log.info(clientId + " joined");
            log.info("Currently users: {}", sessionService.getSavedSessionsCount());
        }
        retransmitFrames(session, clientId);
    }

    private void retransmitFrames(WebSocketSession session, String clientId) throws IOException {
        List<TextMessage> drawnFrames = sessionService.getDrawnFrames();
        for (TextMessage t : drawnFrames) {
            session.sendMessage(t);
            log.info("{} requested frame retransmission, sent {} frames", clientId, drawnFrames.size());
        }
    }
}

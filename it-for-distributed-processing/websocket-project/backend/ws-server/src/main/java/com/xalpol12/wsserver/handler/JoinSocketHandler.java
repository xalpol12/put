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

// handle joining scenarios, only way to get into game session
// Scenarios:
// 1. Join new session - no frames retransmitted, clientId saved to map of players
// 2. Join already played session - frames retransmitted, clientId saved to map of players
// 3. Rejoin already played session - frames retransmitted, no clientId saved as it's already in the map
@Slf4j
@Component
@RequiredArgsConstructor
public class JoinSocketHandler extends TextWebSocketHandler {

    private final SessionService sessionService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (!sessionService.isDrawnFramesEmpty()) {
            retransmitFrames(session);
        }
    }

    private void retransmitFrames(WebSocketSession session) throws IOException {
        List<TextMessage> drawnFrames = sessionService.getDrawnFrames();
        for (TextMessage t : drawnFrames) {
            session.sendMessage(t);
            log.info("Requested frame retransmission, sent {} frames", drawnFrames.size());
        }
    }
}

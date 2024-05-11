package com.xalpol12.wsserver.handler;

import com.xalpol12.wsserver.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class JoinSocketHandler extends TextWebSocketHandler {

    private final SessionService sessionService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id = session.getId();
        log.info(id + " joined");

        sessionService.addToSessions(session);
        log.info("Currently users: {}", sessionService.getSavedSessionsCount());

        TextMessage ack = new TextMessage(id);
        session.sendMessage(ack);
        super.afterConnectionEstablished(session);
    }
}

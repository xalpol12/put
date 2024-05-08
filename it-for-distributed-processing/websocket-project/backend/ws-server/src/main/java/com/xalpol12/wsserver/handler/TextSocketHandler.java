package com.xalpol12.wsserver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class TextSocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // i want to replay the history of point drawings by id from 1 to ...
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // get data from frame
        // store the history
        for (WebSocketSession s: sessions) {
            s.sendMessage(message); // send formatted frame to clients
        }
        log.info("Message {} sent to {} clients", message, sessions.size());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info(session.getId() + " joined");
        log.info("Currently users: {}", sessions.size());
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO: change to store session here
        sessions.remove(session);
        log.info(session.getId() + " disconnected");
        log.info("Currently users: {}", sessions.size());
        super.afterConnectionClosed(session, status);
    }
}

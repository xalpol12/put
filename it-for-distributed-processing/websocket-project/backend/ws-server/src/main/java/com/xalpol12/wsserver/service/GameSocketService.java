package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.events.GameEventListener;
import com.xalpol12.wsserver.events.GameTimeUpdateEvent;
import com.xalpol12.wsserver.events.NewWordEvent;
import com.xalpol12.wsserver.events.ScoreUpdateEvent;
import com.xalpol12.wsserver.model.message.CustomMessage;
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
public class GameSocketService implements GameEventListener {
    private final GameSessionService gameSessionService;
    private final GameSocketSender sender;

    public void handleHandshakeMessage(WebSocketSession session, HandshakePayload payload) throws IOException {
        gameSessionService.mapWebSocketSession(session, payload);
        List<String> drawnFrames = gameSessionService.getDrawnFrames(payload.getSessionId());
        List<CustomMessage> messages = drawnFrames.stream()
                .map((m) -> {
                    DrawingPayload d = convertTextMessageToDrawingPayload(m);
                    return CustomMessage.createDrawingMessage(d);
                })
                .toList();
        List<TextMessage> customMessageString = messages.stream()
                .map(GameSocketSender::wrapCustomMessage)
                .toList();
        sender.sendMessages(session, customMessageString);
    }

    public void handleDrawingMessage(WebSocketSession session, DrawingPayload payload) throws IOException {
        if (!gameSessionService.hasDrawingPermission(session)) return;
        gameSessionService.addToDrawnFrames(session, payload.getDrawingFrame());
        CustomMessage m = CustomMessage.createDrawingMessage(payload);
        String senderId = session.getId();
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsFromGameSessionByWSSession(session);
        for (WebSocketSession s : sessions) {
            String sessionId = s.getId();
            if (!sessionId.equals(senderId)) { // do not transmit to the one that sent a frame
                s.sendMessage(GameSocketSender.wrapCustomMessage(m));
            }
        }
        log.info("Transmitted frame to {} clients", sessions.size() - 1);
    }

    public void handleChatMessage(WebSocketSession session, ChatMessagePayload payload) throws IOException {
        log.info("Received chat message {}", payload.getContent());
        ChatMessagePayload serverResponse = gameSessionService.processMessage(session, payload);
        CustomMessage message = CustomMessage.createChatMessage(serverResponse);
        TextMessage tMessage = GameSocketSender.wrapCustomMessage(message);
        if (serverResponse.getSender().equals("SERVER")) { // send server message only to user that sent the message
            session.sendMessage(tMessage);
        } else {
            Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsFromGameSessionByWSSession(session);
            for (WebSocketSession s : sessions) {
                s.sendMessage(tMessage);
            }
        }
    }

    public void sendGameDataMessage(String sessionId, NewWordPayload payload) throws IOException {
        CustomMessage m = CustomMessage.createGameDataMessage(payload);
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsBySessionId(sessionId);
        for (WebSocketSession s : sessions) {
            s.sendMessage(GameSocketSender.wrapCustomMessage(m));
        }
        log.info("GameData sent {}", payload.toString());
    }

    public void sendGameTimerMessage(String sessionId, GameTimerPayload payload) throws IOException {
        CustomMessage m = CustomMessage.createGameTimerMessage(payload);
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsBySessionId(sessionId);
        for (WebSocketSession s : sessions) {
            s.sendMessage(GameSocketSender.wrapCustomMessage(m));
        }
    }

    @Override
    public void onGameTimeUpdate(GameTimeUpdateEvent event) {
        GameTimerPayload payload = new GameTimerPayload(event.getRemainingTime());
        try {
            sendGameTimerMessage(event.getSessionId(), payload);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onNewWord(NewWordEvent event) {
        NewWordPayload payload = new NewWordPayload();
        try {
            sendGameDataMessage(event.getSessionId(), payload);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onScoreUpdate(ScoreUpdateEvent event) {

    }

    public void removeWebSocketSessionFromMap(WebSocketSession session) {
        gameSessionService.removeWebSocketSessionFromMap(session);
    }

    private DrawingPayload convertTextMessageToDrawingPayload(String m) {
        return new DrawingPayload(m);
    }
}

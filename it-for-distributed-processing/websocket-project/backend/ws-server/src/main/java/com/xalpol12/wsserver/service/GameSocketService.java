package com.xalpol12.wsserver.service;

import com.xalpol12.wsserver.events.*;
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
        List<TextMessage> messages = drawnFrames.stream()
                .map((m) -> {
                    DrawingPayload d = convertTextMessageToDrawingPayload(m);
                    return CustomMessage.createDrawingMessage(d);
                })
                .map(GameSocketSender::wrapCustomMessage)
                .toList();
        sender.sendMessages(session, messages);

        List<GameScorePayload> gsp = gameSessionService.getAllPlayersData(payload.getSessionId());
        List<TextMessage> textMessages = gsp.stream()
                .map(CustomMessage::createGameScoreMessage)
                .map(GameSocketSender::wrapCustomMessage)
                .toList();
        sender.sendMessages(session, textMessages);
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

    public void sendGameTimerMessage(String sessionId, GameTimerPayload payload) throws IOException {
        CustomMessage m = CustomMessage.createGameTimerMessage(payload);
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsBySessionId(sessionId);
        for (WebSocketSession s : sessions) {
            s.sendMessage(GameSocketSender.wrapCustomMessage(m));
        }
    }

    public void sendNewWordMessage(String sessionId, NewWordPayload payload) throws IOException {
        CustomMessage m = CustomMessage.createNewWordMessage(payload);
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsBySessionId(sessionId);
        for (WebSocketSession s : sessions) {
            s.sendMessage(GameSocketSender.wrapCustomMessage(m));
        }
        log.info("NewWord sent {}", payload.toString());
    }

    public void sendGameScoreMessage(String sessionId, GameScorePayload payload) throws IOException {
        CustomMessage m = CustomMessage.createGameScoreMessage(payload);
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsBySessionId(sessionId);
        for (WebSocketSession s : sessions) {
            s.sendMessage(GameSocketSender.wrapCustomMessage(m));
        }
        log.info("GameScore sent {}", payload.toString());
    }

    public void sendClearBoardMessage(String sessionId, ClearBoardPayload payload) throws IOException {
        CustomMessage m = CustomMessage.createClearBoardMessage(payload);
        Set<WebSocketSession> sessions = gameSessionService.getAllWSSessionsBySessionId(sessionId);
        for (WebSocketSession s : sessions) {
            s.sendMessage(GameSocketSender.wrapCustomMessage(m));
        }
        log.info("ClearBoard sent {}", payload.toString());
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
        NewWordPayload payload = new NewWordPayload(event.getNewWord(), event.getNewDrawer());
        try {
            sendNewWordMessage(event.getSessionId(), payload);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onScoreUpdate(ScoreUpdateEvent event) {
        GameScorePayload payload = new GameScorePayload(event.getUserId(), event.getScore());
        try {
            sendGameScoreMessage(event.getSessionId(), payload);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onClearBoard(ClearBoardEvent event) {
        ClearBoardPayload payload = new ClearBoardPayload(event.getSessionId());
        gameSessionService.clearDrawnFrames(event.getSessionId());
        try {
            sendClearBoardMessage(event.getSessionId(), payload);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    public void removeWebSocketSessionFromMap(WebSocketSession session) {
        gameSessionService.removeWebSocketSessionFromMap(session);
    }

    private DrawingPayload convertTextMessageToDrawingPayload(String m) {
        return new DrawingPayload(m);
    }
}

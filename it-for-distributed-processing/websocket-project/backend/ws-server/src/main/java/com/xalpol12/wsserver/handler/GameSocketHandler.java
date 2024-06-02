package com.xalpol12.wsserver.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xalpol12.wsserver.model.message.CustomMessage;
import com.xalpol12.wsserver.model.message.payload.*;
import com.xalpol12.wsserver.service.GameSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameSocketHandler extends TextWebSocketHandler {

    private final GameSocketService gameSocketService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session,
                                     @NotNull TextMessage message) throws IOException {
        String payload = message.getPayload();
        CustomMessage customMessage = objectMapper.readValue(payload, CustomMessage.class);

        switch (customMessage.getMessageType()) {
            case HANDSHAKE -> {
                HandshakePayload handshakePayload = (HandshakePayload) customMessage.getPayload();
                gameSocketService.handleHandshakeMessage(session, handshakePayload);
            }
            case DRAWING -> {
                DrawingPayload drawingPayload = (DrawingPayload) customMessage.getPayload();
                gameSocketService.handleDrawingMessage(session, drawingPayload);
            }
            case CHAT_MESSAGE -> {
                ChatMessagePayload chatMessagePayload = (ChatMessagePayload) customMessage.getPayload();
                gameSocketService.handleChatMessage(session, chatMessagePayload);
            }
            case GAME_DATA -> {
                GameDataPayload gameDataPayload = (GameDataPayload) customMessage.getPayload();
                gameSocketService.handleGameDataMessage(session, gameDataPayload);
            }
            case GAME_TIMER -> {
                GameTimerPayload gameTimerPayload = (GameTimerPayload) customMessage.getPayload();
                gameSocketService.handleGameTimerMessage(session, gameTimerPayload);
            }
        }
    }


    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session,
                                      @NotNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        gameSocketService.removeWebSocketSessionFromMap(session);
    }
}

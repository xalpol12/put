package com.xalpol12.wsserver.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GameSocketService gameSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Client {} has established connection to session", session.getId());
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session,
                                     @NotNull TextMessage message) {
        String payload = message.getPayload();
        log.info(payload);
        try {
            CustomMessage customMessage = objectMapper.readValue(payload, CustomMessage.class);
            log.info(customMessage.toString());
            log.info("Message type {}", customMessage.getMessageType());
            switch (customMessage.getMessageType()) {
                case HANDSHAKE -> {
                    HandshakePayload handshakePayload = (HandshakePayload) customMessage.getPayload();
                    log.info("Handshake message received");
                    gameSocketService.handleHandshakeMessage(session, handshakePayload);
                }
                case DRAWING -> {
                    DrawingPayload drawingPayload = (DrawingPayload) customMessage.getPayload();
                    log.info("Drawing message received");
                    gameSocketService.handleDrawingMessage(session, drawingPayload);
                }
                case CHAT_MESSAGE -> {
                    ChatMessagePayload chatMessagePayload = (ChatMessagePayload) customMessage.getPayload();
                    log.info("Chat message received");
                    gameSocketService.handleChatMessage(session, chatMessagePayload);
                }
                case GAME_DATA -> {
                    GameDataPayload gameDataPayload = (GameDataPayload) customMessage.getPayload();
                    log.info("Game data message received");
                    gameSocketService.handleGameDataMessage(session, gameDataPayload);
                }
                case GAME_TIMER -> {
                    GameTimerPayload gameTimerPayload = (GameTimerPayload) customMessage.getPayload();
                    log.info("Game timer message received");
                    gameSocketService.handleGameTimerMessage(session, gameTimerPayload);
                }
                default -> log.error("Unknown message type: {}", customMessage.getMessageType());
            }
        } catch (JsonMappingException e) {
            log.error("JsonMappingException: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: " + e.getMessage());
        } catch (IOException e) {
            log.error("IOException: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected exception: ", e);
        }
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session,
                                      @NotNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        gameSocketService.removeWebSocketSessionFromMap(session);
    }
}

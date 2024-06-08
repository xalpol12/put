package com.xalpol12.wsserver.handler;

import com.xalpol12.wsserver.protos.ChatMessagePayload;
import com.xalpol12.wsserver.protos.CustomMessage;
import com.xalpol12.wsserver.protos.DrawingPayload;
import com.xalpol12.wsserver.protos.HandshakePayload;
import com.xalpol12.wsserver.service.GameSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameSocketHandler extends BinaryWebSocketHandler {

    private final GameSocketService gameSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Client {} has established connection to session", session.getId());
    }

    @Override
    protected void handleBinaryMessage(@NotNull WebSocketSession session,
                                       @NotNull BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
        byte[] payloadBytes = message.getPayload().array();
        log.info("Raw message bytes: {}", Arrays.toString(payloadBytes));

        CustomMessage customMessage = CustomMessage.parseFrom(message.getPayload());
        log.info("Message type: {}", customMessage.getMessageType());
        switch (customMessage.getMessageType()) {
            case HANDSHAKE -> {
                HandshakePayload handshakePayload = customMessage.getHandshakePayload();
                log.info("Handshake message received userId: {}", handshakePayload.getUserId());
                gameSocketService.handleHandshakeMessage(session, handshakePayload);
            }
            case DRAWING -> {
                DrawingPayload drawingPayload = customMessage.getDrawingPayload();
                log.info("Drawing message received, frame: {}", drawingPayload.getDrawingFrame());
                gameSocketService.handleDrawingMessage(session, drawingPayload);
            }
            case CHAT_MESSAGE -> {
                ChatMessagePayload chatMessagePayload = customMessage.getChatMessagePayload();
                log.info("Chat message received");
                gameSocketService.handleChatMessage(session, chatMessagePayload);
            }
            default -> log.error("Unknown message type: {}", customMessage.getMessageType());
        }
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session,
                                      @NotNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        gameSocketService.removeWebSocketSessionFromMap(session);
    }
}

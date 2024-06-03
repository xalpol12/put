package com.xalpol12.wsserver.model.message;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xalpol12.wsserver.model.message.payload.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CustomMessageDeserializer extends JsonDeserializer<CustomMessage> {
    @Override
    public CustomMessage deserialize(JsonParser p, DeserializationContext cxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode messageNode = mapper.readTree(p);

        MessageType messageType = MessageType.valueOf(messageNode.get("messageType").asText());
        JsonNode payloadNode = messageNode.get("payload");

        log.info("Deserializer found type: {}", messageType.name());

        Payload payload;
        switch (messageType) {
            case HANDSHAKE -> payload = mapper.treeToValue(payloadNode, HandshakePayload.class);
            case DRAWING -> payload = mapper.treeToValue(payloadNode, DrawingPayload.class);
            case CHAT_MESSAGE -> payload = mapper.treeToValue(payloadNode, ChatMessagePayload.class);
            case GAME_DATA -> payload = mapper.treeToValue(payloadNode, NewWordPayload.class);
            case GAME_TIMER -> payload = mapper.treeToValue(payloadNode, GameTimerPayload.class);
            default -> throw new IllegalArgumentException("Unknown message type: " + messageType);
        }
        log.info("Deserialized object of type " + messageType);
        return new CustomMessage(messageType, payload);
    }
}

package com.xalpol12.wsserver.model.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.xalpol12.wsserver.model.message.payload.*;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "messageType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HandshakePayload.class, name = "HANDSHAKE"),
        @JsonSubTypes.Type(value = DrawingPayload.class, name = "DRAWING"),
        @JsonSubTypes.Type(value = ChatMessagePayload.class, name = "CHAT_MESSAGE"),
        @JsonSubTypes.Type(value = GameDataPayload.class, name = "GAME_DATA"),
        @JsonSubTypes.Type(value = GameTimerPayload.class, name = "GAME_TIMER")
})
@Data
public class CustomMessage {
    private MessageType messageType;
    private Object payload;

    public CustomMessage(MessageType type, Object payload) {
        messageType = type;
        this.payload = payload;
    }
}

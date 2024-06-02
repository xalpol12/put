package com.xalpol12.wsserver.model.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xalpol12.wsserver.model.message.payload.Payload;
import lombok.Data;

@Data
@JsonDeserialize(using = CustomMessageDeserializer.class)
public class CustomMessage {
    private MessageType messageType;
    private Payload payload;

    public CustomMessage() {
    }

    public CustomMessage(MessageType type, Payload payload) {
        this.messageType = type;
        this.payload = payload;
    }
}

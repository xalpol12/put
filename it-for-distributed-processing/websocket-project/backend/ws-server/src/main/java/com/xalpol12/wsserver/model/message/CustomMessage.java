package com.xalpol12.wsserver.model.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xalpol12.wsserver.model.message.payload.*;
import lombok.Data;

@Data
@JsonDeserialize(using = CustomMessageDeserializer.class)
public class CustomMessage {
    private MessageType messageType;
    private Payload payload;

    public CustomMessage(MessageType type, Payload payload) {
        messageType = type;
        this.payload = payload;
    }

    public static CustomMessage createChatMessage(ChatMessagePayload c) {
        return new CustomMessage(MessageType.CHAT_MESSAGE, c);
    }

    public static CustomMessage createGameDataMessage(NewWordPayload gd) {
        return new CustomMessage(MessageType.GAME_DATA, gd);
    }

    public static CustomMessage createGameTimerMessage(GameTimerPayload gt) {
        return new CustomMessage(MessageType.GAME_TIMER, gt);

    }

    public static CustomMessage createDrawingMessage(DrawingPayload d) {
        return new CustomMessage(MessageType.DRAWING, d);
    }
}

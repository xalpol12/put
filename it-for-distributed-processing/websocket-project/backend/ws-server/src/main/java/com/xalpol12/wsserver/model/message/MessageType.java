package com.xalpol12.wsserver.model.message;

import lombok.Getter;

@Getter
public enum MessageType {
    HANDSHAKE(0),
    DRAWING(1),
    CHAT_MESSAGE(2),
    GAME_DATA(3),
    GAME_TIMER(4);

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public static MessageType fromValue(int value) {
        for (MessageType type : values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type value: " + value);
    }

}

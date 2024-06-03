package com.xalpol12.wsserver.model.message;

import lombok.Getter;

@Getter
public enum MessageType {
    HANDSHAKE,
    DRAWING,
    CHAT_MESSAGE,
    GAME_TIMER,
    NEW_WORD,
    GAME_SCORE;
}

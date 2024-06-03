package com.xalpol12.wsserver.events;

import lombok.Getter;

@Getter
public class GameTimeUpdateEvent extends GameEvent {
    private final int remainingTime;

    public GameTimeUpdateEvent(String sessionId, int remainingTime) {
        super(sessionId);
        this.remainingTime = remainingTime;
    }
}

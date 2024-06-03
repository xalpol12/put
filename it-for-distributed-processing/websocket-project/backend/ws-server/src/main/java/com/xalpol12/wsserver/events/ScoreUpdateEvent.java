package com.xalpol12.wsserver.events;

import lombok.Getter;

@Getter
public class ScoreUpdateEvent extends GameEvent {
    private final String userId;
    private final Integer score;

    public ScoreUpdateEvent(String sessionId, String userId, Integer score) {
        super(sessionId);
        this.userId = userId;
        this.score = score;
    }
}

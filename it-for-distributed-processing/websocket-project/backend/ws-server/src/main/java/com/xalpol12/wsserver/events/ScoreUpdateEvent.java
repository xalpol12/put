package com.xalpol12.wsserver.events;

import com.xalpol12.wsserver.model.PlayerData;
import lombok.Getter;

import java.util.Map;

@Getter
public class ScoreUpdateEvent extends GameEvent {
    private final Map<String, PlayerData> userDataMap;

    public ScoreUpdateEvent(String sessionId, Map<String, PlayerData> userDataMap) {
        super(sessionId);
        this.userDataMap = userDataMap;
    }
}

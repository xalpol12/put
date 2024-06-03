package com.xalpol12.wsserver.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameEvent {
    private final String sessionId;
}

package com.xalpol12.wsserver.events;

public class ClearBoardEvent extends GameEvent {

    public ClearBoardEvent(String sessionId) {
        super(sessionId);
    }
}

package com.xalpol12.wsserver.events;

import lombok.Getter;

@Getter
public class NewWordEvent extends GameEvent {
    private final String newWord;
    private final String newDrawer;

    public NewWordEvent(String sessionId, String newWord, String newDrawer) {
        super(sessionId);
        this.newWord = newWord;
        this.newDrawer = newDrawer;
    }
}

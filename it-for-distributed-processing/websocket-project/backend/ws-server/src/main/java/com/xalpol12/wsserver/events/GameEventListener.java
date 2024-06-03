package com.xalpol12.wsserver.events;

public interface GameEventListener {
    void onGameTimeUpdate(GameTimeUpdateEvent event);

    void onNewWord(NewWordEvent event);

    void onScoreUpdate(ScoreUpdateEvent event);
}
